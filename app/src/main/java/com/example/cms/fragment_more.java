package com.example.cms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_more extends Fragment {

    public CircleImageView clubLogo;
    public TextView username;
    public TextView email;
    public EditText fullname;
    public EditText thought;
    public EditText officiallink;

    public AppCompatButton logo_button;
    public Button update;
    public  Button logout;

    private static String currentUser;
    private FirebaseStorage storage;
    private DatabaseReference reference;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_more, container, false);
        View view =  inflater.inflate(R.layout.fragment_more, container, false);

        clubLogo= view.findViewById(R.id.more_profile_pic);
        username= view.findViewById(R.id.more_uname);
        email= view.findViewById(R.id.more_email);
        thought= view.findViewById(R.id.more_thought);
        fullname= view.findViewById(R.id.more_name);
        officiallink= view.findViewById(R.id.more_link);

        logo_button=view.findViewById(R.id.more_btn_edit_profile_img);
        update = view.findViewById(R.id.more_update);
        logout = view.findViewById(R.id.logout);

        reference= FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

//        Toast.makeText(getContext(), currentUser, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();

        username.setText(currentUser);
        email.setText(user.getEmail().toString());
        reference.child("Club").child(currentUser).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thought.setText(snapshot.child("Thought").getValue(String.class));
                fullname.setText(snapshot.child("FullName").getValue(String.class));
                officiallink.setText(snapshot.child("Official Link").getValue(String.class));
                String uri= snapshot.child("Logo").getValue(String.class);
                Glide.with(clubLogo.getContext()).load(uri).into(clubLogo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(fragment_more.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Club").child(currentUser).child("Profile").child("FullName").setValue(fullname.getText().toString());
                reference.child("Club").child(currentUser).child("Profile").child("Thought").setValue(thought.getText().toString());
                reference.child("Club").child(currentUser).child("Profile").child("Official Link").setValue(officiallink.getText().toString());

                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }

     @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            Uri uri = data.getData();
            clubLogo.setImageURI(uri);

            StorageReference MBreference= storage.getReference().child(currentUser +"/logo.jpg");
            MBreference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    MBreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            reference.child("Club").child(currentUser).child("Profile").child("Logo").setValue(uri.toString());
                        }
                    });
                }
            });
         Toast.makeText(getContext(), "Logo Changed", Toast.LENGTH_SHORT).show();
        }
}