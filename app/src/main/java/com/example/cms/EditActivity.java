package com.example.cms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity
{
    public TextView txtname;
    public TextView txtpost;
    public AppCompatButton button1;
    public AppCompatButton button2;
    public AppCompatButton button3;
    public AppCompatButton button4;
    public ImageView imageView;
    public int check;

    private FirebaseStorage storage;
    private FirebaseUser user;
    private DatabaseReference reference;
    public static String position;
    private static String currentUser = "WSM";
    private mainAdapter adapter;
    private ArrayList<main> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_for_all_members);
        txtname = findViewById(R.id.txt_edit_name);
        txtpost = findViewById(R.id.txt_post_name);

        button1 = findViewById(R.id.btn_edit);
        button2 = findViewById(R.id.btn_edit_post);
        button3 = findViewById(R.id.btn_edit_profile_img);
        button4 = findViewById(R.id.btn_member_delete);

        imageView = findViewById(R.id.profile_pic);

        reference= FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

        Intent intent = getIntent();
        position = intent.getStringExtra("pos");
        check = intent.getIntExtra("CALLED_FROM",0);

        switch (check)
        {
            case 1 :
                Toast.makeText(getApplicationContext(),"Setting from mentor board",Toast.LENGTH_SHORT).show();

                reference.child("Club").child(currentUser).child("Board").child("Mentor").child(position).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        DataSnapshot snapshot= task.getResult();

                        String uri = snapshot.child("Photo").getValue().toString();
                        Glide.with(imageView.getContext()).load(uri).into(imageView);
                        txtname.setText(String.valueOf(snapshot.child("Name").getValue()));
                        txtpost.setText(String.valueOf(snapshot.child("Position").getValue()));
                    }
                });
                break;
            case 2 :
                Toast.makeText(getApplicationContext(),"Setting from main board",Toast.LENGTH_SHORT).show();
                reference.child("Club").child(currentUser).child("Board").child("Main").child(position).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot snapshot= task.getResult();

                        String uri = snapshot.child("Photo").getValue().toString();
                        Glide.with(imageView.getContext()).load(uri).into(imageView);
                        txtname.setText(String.valueOf(snapshot.child("Name").getValue()));
                        txtpost.setText(String.valueOf(snapshot.child("Position").getValue()));
                    }
                });
                break;
            case 3 :
                Toast.makeText(getApplicationContext(),"setting member from Assistant board",Toast.LENGTH_SHORT).show();
                reference.child("Club").child(currentUser).child("Board").child("Assistant").child(position).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot snapshot= task.getResult();

                        String uri = snapshot.child("Photo").getValue().toString();
                        Glide.with(imageView.getContext()).load(uri).into(imageView);
                        txtname.setText(String.valueOf(snapshot.child("Name").getValue()));
                        txtpost.setText(String.valueOf(snapshot.child("Position").getValue()));
                    }
                });
                break;
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                com.example.cms.BottomSheetDialog bottomSheet = new com.example.cms.BottomSheetDialog(check, position);
                bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                com.example.cms.BottomSheetDailogForPost bottomSheetDailogForPost = new com.example.cms.BottomSheetDailogForPost(check);
                bottomSheetDailogForPost.show(getSupportFragmentManager(),"ModalBottomSheet");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(EditActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (check)
                {
                    case 1 :
                        Toast.makeText(getApplicationContext(),"Delete member from mentor board",Toast.LENGTH_SHORT).show();
                        //Delete member from mentor board
                        reference.child("Club").child(currentUser).child("Board").child("Mentor").child(position).removeValue();
                        break;
                    case 2 :
                        Toast.makeText(getApplicationContext(),"Delete member from main board",Toast.LENGTH_SHORT).show();
                        //Delete member from main board
                        reference.child("Club").child(currentUser).child("Board").child("Main").child(position).removeValue();
                        break;
                    case 3 :
                        Toast.makeText(getApplicationContext(),"Delete member from Assistant board",Toast.LENGTH_SHORT).show();
                        //Delete member from Assistant board
                        reference.child("Club").child(currentUser).child("Board").child("Assistant").child(position).removeValue();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        imageView.setImageURI(uri);
        switch (check)
        {
            case 1 :
                Toast.makeText(getApplicationContext(),"save img to firebase for mentor board",Toast.LENGTH_SHORT).show();
                //save img to firebase for mentor board
                StorageReference MBreference= storage.getReference().child(currentUser+"/Mentor Board/"+position+".jpg");
                MBreference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        MBreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("Club").child(currentUser).child("Board").child("Mentor").child(position).child("Photo").setValue(uri.toString());
                            }
                        });
                    }
                });
                break;
            case 2 :
                Toast.makeText(getApplicationContext(),"save img to firebase for main board",Toast.LENGTH_SHORT).show();
                //save img to firebase for main
                StorageReference MaBreference= storage.getReference().child(currentUser+"/Main Board/"+position+".jpg");
                MaBreference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        MaBreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("Club").child(currentUser).child("Board").child("Main").child(position).child("Photo").setValue(uri.toString());
                            }
                        });
                    }
                });
                break;
            case 3 :
                Toast.makeText(getApplicationContext(),"save img to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                //save img to firebase for Assistant board
                StorageReference ABreference = storage.getReference().child(currentUser+"/Assistant Board/"+position+".jpg");
                ABreference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ABreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("Club").child(currentUser).child("Board").child("Assistant").child(position).child("Photo").setValue(uri.toString());
                            }
                        });
                    }
                });
                break;
        }
    }
}
