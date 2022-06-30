package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cms.EditActivity;
import com.example.cms.MainBoardAddPage;
import com.example.cms.OnMainBoardRowClickListener;
import com.example.cms.main;
import com.example.cms.mainAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_main_board extends Fragment {

    public AppCompatButton button;
    public EditText ed_search;
    public RecyclerView recyclerView;
   // public ArrayList<MainBoardPOJO> arrayList;
    public ArrayList<main> arrayList;
  // public MainBoardAdapter adapter;
    public mainAdapter adapter;
 //   private String currentUser ="WSM";  //user.getCurrentuser();
    private FirebaseUser user;
    private static String currentUser;
    //
    public DatabaseReference database;
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_board, container, false);
        ed_search = view.findViewById(R.id.ed_search);
        recyclerView = view.findViewById(R.id.rv);
        button = view.findViewById(R.id.btn_main_board_add);
        arrayList = new ArrayList<>();

        //Add main member board information from firebase to arrayList
        database= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

        //FirebaseRecyclerOptions<main> options= new FirebaseRecyclerOptions.Builder<main>().setQuery(database.child("club").child("WSM").child("Board").child("Main"),main.class).build();

//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer"));
//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Harshal Gawande","Treasurer"));
//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer"));
//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer"));
//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer"));
//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer"));
//        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new mainAdapter(arrayList, new OnMainBoardRowClickListener() {
            @Override
            public void onItemClick(main mainBoardPOJO) {

                Toast.makeText(getContext(),mainBoardPOJO.getName(),Toast.LENGTH_SHORT).show();
                String position=mainBoardPOJO.getPosition().toString();

                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("CALLED_FROM",2);
                intent.putExtra("pos", position);

                startActivity(intent);
            }
        });


        //Toast.makeText(getContext(), "enter1", Toast.LENGTH_SHORT).show();
        //
        database.child("Club").child(currentUser).child("Board").child("Main").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    main main1=dataSnapshot.getValue(main.class);
                    arrayList.add(main1);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //

        recyclerView.setAdapter(adapter);


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainBoardAddPage.class);
                intent.putExtra("CALLED_FROM",2);
                startActivity(intent);
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<main> filteredlist = new ArrayList<>();
        for (main item : arrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            adapter.filterList(filteredlist);
        }
    }
}