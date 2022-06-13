package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_event extends Fragment {

    public AppCompatButton btn_organize_event;
    public RecyclerView rv;
    public ArrayList<com.example.cms.event> arrayList;

    public DatabaseReference database;
    private Adapter adapter;
    //private String currentUser ="WSM";  //user.getCurrentuser();

    private FirebaseUser user;
    private static String currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_event, container, false);

        btn_organize_event = view.findViewById(R.id.btn_organize);
        rv = view.findViewById(R.id.rv_event);
        arrayList = new ArrayList<>();


//        arrayList.add(new EventPOJO("Event1","12/12/1200","Generating random paragraphs can be an excellent way for writers to get their creative flow going at the beginning of the day. The writer has no idea what topic the random paragraph will be about when it appears. This forces the writer to use creativity to complete one of three common writing challenges. The writer can use the paragraph as the first one of a short story and build upon it. A second option is to use the random paragraph somewhere in a short story they create. The third option is to have the random paragraph be the ending paragraph in a short story. No matter which of these challenges is undertaken, the writer is forced to use creativity to incorporate the paragraph into their writing."));
//        arrayList.add(new EventPOJO("Event2","03/02/1200","Generating random paragraphs can be an excellent way for writers to get their creative flow going at the beginning of the day. The writer has no idea what topic the random paragraph will be about when it appears. This forces the writer to use creativity to complete one of three common writing challenges. The writer can use the paragraph as the first one of a short story and build upon it. A second option is to use the random paragraph somewhere in a short story they create. The third option is to have the random paragraph be the ending paragraph in a short story. No matter which of these challenges is undertaken, the writer is forced to use creativity to incorporate the paragraph into their writing.\""));
//        arrayList.add(new EventPOJO("Event3","02/11/1200","Sunday"));
//        arrayList.add(new EventPOJO("Event4","04/09/1200","Sunday"));
//        arrayList.add(new EventPOJO("Event5","05/10/1200","Sunday"));

//        Toast.makeText(getContext(),"enter1",Toast.LENGTH_SHORT).show();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        database= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

        adapter=new Adapter(arrayList, new OnEventClickListener() {
            @Override
            public void onItemClicked(com.example.cms.event Event) {
               // Toast.makeText(getContext(),event.getName(),Toast.LENGTH_SHORT).show();
            }
        });
//        rv.setAdapter(new EventAdapter(arrayList, new OnEventClickListener() {
//            @Override
//            public void onItemClicked(EventPOJO eventPOJO) {
//                Toast.makeText(getContext(),eventPOJO.getEvent_name(),Toast.LENGTH_SHORT).show();
//            }
//        }));
//        Toast.makeText(getContext(),"enter2",Toast.LENGTH_SHORT).show();
//
//
//
//        Toast.makeText(getContext(),"enter4",Toast.LENGTH_SHORT).show();



        btn_organize_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), com.example.cms.EventActivity.class);
                startActivity(intent);
            }
        });
        //
        database.child("Club").child(currentUser).child("Event").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    com.example.cms.event event=dataSnapshot.getValue(com.example.cms.event.class);
                    arrayList.add(event);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //
        //Toast.makeText(getContext(),"enter3",Toast.LENGTH_SHORT).show();

        rv.setAdapter(adapter);

        return view;
    }
}