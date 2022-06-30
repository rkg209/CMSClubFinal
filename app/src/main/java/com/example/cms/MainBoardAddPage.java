package com.example.cms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class MainBoardAddPage extends AppCompatActivity
{
    public AppCompatButton btn_add_image;
    public AppCompatButton btn_save;
    public ImageView profile;
    public EditText ed_new_member_name;
    public EditText ed_new_member_post;
    int check;

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private FirebaseStorage storage;
    private static String currentUser;
    private static String logouri ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board_add_page);
        btn_add_image = findViewById(R.id.btn_add_image);
        profile = findViewById(R.id.profile_pic);
        btn_save = findViewById(R.id.btn_save);
        ed_new_member_name = findViewById(R.id.ed_new_member_name);
        ed_new_member_post = findViewById(R.id.ed_new_member_psot);

        dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

        Intent intent = getIntent();
        check = intent.getIntExtra("CALLED_FROM",0);

        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MainBoardAddPage.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebase code to add member details to database
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                String name = ed_new_member_name.getText().toString();
                String post = ed_new_member_post.getText().toString();

                //String clubName = currentUser.getDisplayName();
                //String clubName = "WSM";

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Name", name);
                hashMap.put("Position", post);
                if(logouri.equals("")){hashMap.put("Photo","");}


                switch (check)
                {
                    case 1 :
                        //Toast.makeText(getApplicationContext(),"save data to firebase for mentor board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for mentor board

                        dbRef.child("Club").child(currentUser).child("Board").child("Mentor").child(post).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainBoardAddPage.this, "Member Added to Mentor Board", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 2 :
                        //Toast.makeText(getApplicationContext(),"data img to firebase for main board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for main board

                        dbRef.child("Club").child(currentUser).child("Board").child("Main").child(post).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainBoardAddPage.this, "Member Added to Main Board", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 3 :
                        //Toast.makeText(getApplicationContext(),"data img to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for Assistant board
                        dbRef.child("Club").child(currentUser).child("Board").child("Assistant").child(post).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainBoardAddPage.this, "Member Added to Assistant Board", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        profile.setImageURI(uri);
        //String clubName = "WSM";
        String post = ed_new_member_post.getText().toString();
        logouri=uri.toString();

        switch (check)
        {
            case 1 :
                //Toast.makeText(getApplicationContext(),"save img to firebase for mentor board",Toast.LENGTH_SHORT).show();
                //save img to firebase for mentor board
                StorageReference MBreference= storage.getReference().child(currentUser+"/Mentor Board/"+post+".jpg");
                MBreference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        MBreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("Club").child(currentUser).child("Board").child("Mentor").child(post).child("Photo").setValue(uri.toString());
                            }
                        });
                    }
                });
                break;
            case 2 :
                //Toast.makeText(getApplicationContext(),"save img to firebase for main board",Toast.LENGTH_SHORT).show();
                //save img to firebase for main board
                StorageReference Mreference= storage.getReference().child(currentUser+"/Main Board/"+post+".jpg");
                Mreference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Mreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("Club").child(currentUser).child("Board").child("Main").child(post).child("Photo").setValue(uri.toString());
                            }
                        });
                    }
                });
                break;
            case 3 :
                //Toast.makeText(getApplicationContext(),"save img to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                //save img to firebase for Assistant board
                StorageReference Areference= storage.getReference().child(currentUser+"/Assitant Board/"+post+".jpg");
                Areference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Areference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("Club").child(currentUser).child("Board").child("Assistant").child(post).child("Photo").setValue(uri.toString());
                            }
                        });
                    }
                });
                break;
        }
    }
}
