package com.example.cms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_home extends Fragment {
    public ArrayList<SlideModel> arrayList;
    public ImageSlider imageSlider;
    public ImageSlider imageSlider2;
    public CircleImageView clubLogo;
    public TextView username;
    public TextView thought;

    private static String currentUser;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.image_slider);

        imageSlider2 = view.findViewById(R.id.image_slider2);

        clubLogo= view.findViewById(R.id.clubLogo);
        username= view.findViewById(R.id.clubUserName);
        thought= view.findViewById(R.id.clubThought);

        user=FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

        username.setText(currentUser);

        FirebaseDatabase.getInstance().getReference().child("Club").child(currentUser).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thought.setText(snapshot.child("Thought").getValue(String.class));
                String uri= snapshot.child("Logo").getValue(String.class);
                Glide.with(clubLogo.getContext()).load(uri).into(clubLogo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        arrayList = new ArrayList<>();
        final List<SlideModel> list=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Club").child(currentUser).child("Event").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){

                    String uri=data.child("Banner").getValue().toString();
                    list.add(new SlideModel(uri,ScaleTypes.FIT));
                    imageSlider.setImageList(list,ScaleTypes.FIT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final List<SlideModel> list2=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Club").child(currentUser).child("Club Service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){

                    String uri=data.child("Banner").getValue().toString();
                    list2.add(new SlideModel(uri,ScaleTypes.FIT));
                    imageSlider2.setImageList(list2,ScaleTypes.FIT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        arrayList.add(new SlideModel(R.drawable.img_example,"My Photo",null));
//        arrayList.add(new SlideModel(R.drawable.img_1,null));
//        arrayList.add(new SlideModel(R.drawable.img_2,null));
//        arrayList.add(new SlideModel(R.drawable.img_3,null));
//        arrayList.add(new SlideModel(R.drawable.img_4,null));
       // imageSlider.setImageList(arrayList, ScaleTypes.FIT);
        //imageSlider2.setImageList(arrayList,ScaleTypes.FIT);
        return view;
    }
}