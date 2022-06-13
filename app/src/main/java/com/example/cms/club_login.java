package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class club_login extends Fragment {

    EditText ed_id;
    EditText ed_pass;
    AppCompatButton btn_login;
    TextView txt_forget;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_club_login, container, false);

        ed_id = view.findViewById(R.id.ed_id);
        ed_pass = view.findViewById(R.id.ed_pass);
        btn_login = view.findViewById(R.id.btn_login);

        auth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = ed_id.getText().toString();
                String Pass = ed_pass.getText().toString();
                if ((Email.equals("")) || (Pass.equals(""))){
                    Toast.makeText(getContext(), "Enter your Credentials", Toast.LENGTH_SHORT).show();
                }else {
                    signIn(Email, Pass);
                }
            }
        });
        return view;
    }

    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            if(name.equals(email)){                 //checks if user is loging in for first time
                                startActivity(new Intent(getContext(), com.example.cms.UpdateProfile.class));
                            }
                            else{            startActivity(new Intent(getContext(), com.example.cms.ClubHomePage.class));
                            }
                            //updateUI(user);
                        } else {
                            Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getContext(), com.example.cms.ClubHomePage.class));
        }
    }
}