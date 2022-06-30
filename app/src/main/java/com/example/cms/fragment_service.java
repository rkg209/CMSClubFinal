//package com.example.cms;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link fragment_service#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class fragment_service extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public fragment_service() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment fragment_service.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static fragment_service newInstance(String param1, String param2) {
//        fragment_service fragment = new fragment_service();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_service, container, false);
//    }
//}

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

public class fragment_service extends Fragment {

    public AppCompatButton btn_organize_event;
    public RecyclerView rv;
    public ArrayList<com.example.cms.event> arrayList;

    public DatabaseReference database;
    private CSAdapter adapter;
    //private String currentUser ="WSM";  //user.getCurrentuser();

    private FirebaseUser user;
    private static String currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_service, container, false);

        btn_organize_event = view.findViewById(R.id.btn_organize_cs);
        rv = view.findViewById(R.id.rv_event_cs);
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

        adapter=new CSAdapter(arrayList, new OnEventClickListener() {
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

                Intent intent = new Intent(getActivity(), com.example.cms.CSActivity.class);
                startActivity(intent);
            }
        });
        //
        database.child("Club").child(currentUser).child("Club Service").addValueEventListener(new ValueEventListener() {
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