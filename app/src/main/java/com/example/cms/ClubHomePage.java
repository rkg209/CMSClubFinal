package com.example.cms;

/*
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cms.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClubHomePage extends AppCompatActivity {
    private Button so;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home_page);

        so= findViewById(R.id.CSignOut);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(this, "Welcome Back " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
  //      Toast.makeText(this, "Welcome Back " + user.getEmail(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Welcome Back " + user.getUid(), Toast.LENGTH_SHORT).show();

        so.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClubHomePage.this, "Signing Out...", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
*/

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClubHomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home_page);
        bottomNavigationView = findViewById(R.id.botttomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new com.example.cms.fragment_home()).commit();

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            switch (item.getItemId())
            {
                case R.id.home:
                    fragment = new com.example.cms.fragment_home();
                    break;
                case R.id.members:
                    fragment = new com.example.cms.fragments_members();
                    break;
                case R.id.events:
                    fragment = new com.example.cms.fragment_event();
                    break;
                case R.id.services:
                    fragment = new com.example.cms.fragment_service();
                    break;
                case R.id.more:
                    fragment = new com.example.cms.fragment_more();
                    break;
            }

            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
            return true;
        });
    }
}