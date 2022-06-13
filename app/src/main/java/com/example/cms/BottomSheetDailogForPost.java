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

public class BottomSheetDailogForPost extends BottomSheetDialogFragment
{
    public EditText ed_name;
    public AppCompatButton save;
    public AppCompatButton cancel;
    public int check;

    public BottomSheetDailogForPost(int check)
    {
        this.check = check;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_dailog_for_poat, container, false);
        ed_name = v.findViewById(R.id.ed_post);
        save = v.findViewById(R.id.btn_save);
        cancel = v.findViewById(R.id.btn_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();

                switch (check)
                {
                    case 1 :
                        Toast.makeText(getContext(),"save data to firebase for mentor board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for mentor board
                        break;
                    case 2 :
                        Toast.makeText(getContext(),"save data to firebase for main board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for main board
                        break;
                    case 3 :
                        Toast.makeText(getContext(),"save data to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for Assistant board
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
