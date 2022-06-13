package com.example.cms;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cms.ClubHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    private EditText updateName;
    private Button updateButton;

    private DatabaseReference dbRef;
    private FirebaseUser user;
    private FirebaseStorage storage;

    ActivityResultLauncher<String> launcher;
    private ImageView logo;
    private static Uri LogoUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance() ;

        updateButton= findViewById(R.id.UPsave);
        updateName = findViewById(R.id.UPusername);
        logo = findViewById(R.id.UPlogo);

 /*       dbRef.child("Club").child("WSM").child("Logo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String img = snapshot.getValue(String.class);
                Picasso.get().load(img).into(logo);                                                                    //To display logo
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                logo.setImageURI(result);

                String username = updateName.getText().toString();
                LogoUri = result;
                StorageReference reference= storage.getReference().child(username +"/logo.jpg");
                reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("Club").child(username).child("Profile").child("Logo").setValue(uri.toString());
                                Toast.makeText(UpdateProfile.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = updateName.getText().toString();
                //Toast.makeText(UpdateProfile.this, username, Toast.LENGTH_SHORT).show();

                updateProfile(username);
                //startActivity(new Intent(UpdateProfile.this, ClubHomePage.class));
                //Toast.makeText(UpdateProfile.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
             }
        });
    }

    public void updateProfile(String name) {

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("UserName", name);
        hashMap.put("Email", user.getEmail());
        hashMap.put("UID", user.getUid());

        dbRef.child("Club").child(name).child("Profile").updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(UpdateProfile.this, "Successfully added on Database", Toast.LENGTH_SHORT).show();
            }
        });

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(UpdateProfile.this, ClubHomePage.class));
                        }
                    }
                });
    }

}