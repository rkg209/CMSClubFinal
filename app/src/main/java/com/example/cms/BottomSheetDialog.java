package com.example.cms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment
{
    public EditText ed_name;
    public AppCompatButton save;
    public AppCompatButton cancel;

    public int check;

    private DatabaseReference reference;
    private FirebaseUser user;
    private static String currentUser;

    private mainAdapter adapter;
    private ArrayList<main> list;
    public String position ;
    //private static String currentUser = "WSM";

    public BottomSheetDialog(int check, String position)
    {
        this.check = check;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
        ed_name = v.findViewById(R.id.ed_name);
        save = v.findViewById(R.id.btn_save);
        cancel = v.findViewById(R.id.btn_cancel);

        reference= FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getDisplayName();

        Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();
                String name=ed_name.getText().toString();

                switch (check)
                {
                    case 1 :
                        Toast.makeText(getContext(),"save data to firebase for mentor board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for mentor board
                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();

//                        adapter=new mainAdapter(list,cd->{
//                            Toast.makeText(getContext(),"Clicked"+cd.getName(),Toast.LENGTH_SHORT).show();
//                            position=cd.getPosition();
//
//                        });
                       reference.child("Club").child(currentUser).child("Board").child("Mentor").child(position).child("Name").setValue(name);
                        break;
                    case 2 :
                        Toast.makeText(getContext(),"save data to firebase for main board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for main board
                        reference.child("Club").child(currentUser).child("Board").child("Main").child(position).child("Name").setValue(name);
                        break;
                    case 3 :
                        Toast.makeText(getContext(),"save data to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for Assistant board
                        reference.child("Club").child(currentUser).child("Board").child("Assistant").child(position).child("Name").setValue(name);
                        break;
                }

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Canceled",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return v;
    }
}
