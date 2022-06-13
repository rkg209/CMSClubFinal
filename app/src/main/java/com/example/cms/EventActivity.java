package com.example.cms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EventActivity extends AppCompatActivity {

    public TextView textEventName;
    public TextView textEventDate;
    public TextView textEventFileUri;
    public TextView textEventDescription;

    public EditText ed_event;

    public AppCompatButton btn_event_edit;
    public AppCompatButton btn_event_edit_save;
    public AppCompatButton btn_event_all_save;
    public AppCompatButton btn_event_poster;
    public AppCompatButton btn_event_date;
    public AppCompatButton btn_event_description;


    public EditText ed_event_description;
    public AppCompatButton btn_event_description_save;


    public ImageView img_event_poster;

    int request_code = 1;

    public String EVENTNAME;
    public String EVENTDATE;
    public String EVENTDESCRIPTION;
    public Uri EVENTPOSTERURI;

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private FirebaseStorage storage;
    private static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organize);

        textEventName = findViewById(R.id.txt_event_name);
        ed_event = findViewById(R.id.ed_event_name);
        btn_event_edit = findViewById(R.id.btn_event_name_edit);
        btn_event_edit_save = findViewById(R.id.btn_event_name_edit_save);

        btn_event_all_save = findViewById(R.id.btn_event_all_save);
        btn_event_date = findViewById(R.id.btn_event_date);
        btn_event_poster = findViewById(R.id.btn_event_file);
        btn_event_description = findViewById(R.id.btn_event_description);

        textEventDate = findViewById(R.id.txt_event_date);
        textEventFileUri = findViewById(R.id.txt_event_file_uri);
        textEventDescription = findViewById(R.id.txt_event_description);

        img_event_poster = findViewById(R.id.img_event_poster);

        dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();


        btn_event_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEventName.setVisibility(View.GONE);
                ed_event.setVisibility(View.VISIBLE);
                btn_event_edit.setVisibility(View.GONE);
                btn_event_edit_save.setVisibility(View.VISIBLE);
            }
        });

        btn_event_edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EVENTNAME = ed_event.getText().toString();
                textEventName.setText(EVENTNAME);
                ed_event.setVisibility(View.GONE);
                btn_event_edit_save.setVisibility(View.GONE);
                textEventName.setVisibility(View.VISIBLE);
                btn_event_edit.setVisibility(View.VISIBLE);


            }
        });

        btn_event_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EventActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        btn_event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        EVENTDATE = materialDatePicker.getHeaderText();
                        textEventDate.setText(EVENTDATE);
                    }
                });


        btn_event_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(EventActivity.this);
                View view2 = getLayoutInflater().inflate(R.layout.multiline_edittext_dialog_box,null);
                alert.setView(view2);
                final AlertDialog alertDialog = alert.create();

                ed_event_description = view2.findViewById(R.id.ed_event_description);
                btn_event_description_save = view2.findViewById(R.id.btn_event_description_save);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                btn_event_description_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EVENTDESCRIPTION = ed_event_description.getText().toString();
                        if(EVENTDESCRIPTION.length() > 20 )
                        {
                            Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                            textEventDescription.setText(EVENTDESCRIPTION.substring(0,20)+"....");
                            alertDialog.dismiss();
                            return;
                        }

                        new SweetAlertDialog(
                                EventActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setContentText("Description must be greater than 20")
                                .show();
                    }
                });
            }
        });

        btn_event_all_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebase code to store event
//                use variable EVENTNAME;
//                use variable EVENTDATE;
//                use variable EVENTPOSTERURI;
//                use variable EVENTDESCRIPTION;
                //store above data in firebase

                //String clubName = "WSM";

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Name", EVENTNAME);
                hashMap.put("Date", EVENTDATE);
                hashMap.put("Description", EVENTDESCRIPTION);

                dbRef.child("Club").child(currentUser).child("Event").child(EVENTDATE).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EventActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
                    }
                });

                HashMap<String,Object> hashMap2 = new HashMap<>();
                hashMap.put("Name", EVENTNAME);
                hashMap.put("Date", EVENTDATE);
                hashMap.put("Club", currentUser);

                dbRef.child("EventList").child(EVENTDATE).updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EventActivity.this, "EventList Added", Toast.LENGTH_SHORT).show();
                    }
                });

                StorageReference reference= storage.getReference().child(currentUser+"/Event/"+EVENTDATE+".jpg");
                reference.putFile(EVENTPOSTERURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("Club").child(currentUser).child("Event").child(EVENTDATE).child("Banner").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EVENTPOSTERURI= data.getData();
        textEventFileUri.setTextSize(10f);
        textEventFileUri.setText(EVENTPOSTERURI.getPath());
        img_event_poster.setVisibility(View.VISIBLE);
        img_event_poster.setImageURI(EVENTPOSTERURI);

    }


}
