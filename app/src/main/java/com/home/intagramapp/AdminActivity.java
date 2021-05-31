package com.home.intagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.home.intagramapp.Fragment.HomeFragment;
import com.home.intagramapp.Fragment.ProfileFragment;
import com.home.intagramapp.Fragment.SearchFragment;
import com.home.intagramapp.Fragment.SearchFragmentForUser;

public class AdminActivity extends AppCompatActivity {
    Button list_user, list_org,chat_org,chat_user;
    FirebaseAuth firebaseAuth;
    Fragment selectedFagrament = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        list_user = findViewById(R.id.list_user);
        list_org = findViewById(R.id.list_org);
        chat_org = findViewById(R.id.chat_org);
        chat_user = findViewById(R.id.chat_user);


       /* button=findViewById(R.id.lbutton);
        firebaseAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(AdminActivity.this,LoginActivity.class));
            }
        });*/
        list_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, OrgSearchActivity.class);

                startActivity(intent);

            }
        });
        chat_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MessageActivityForAdmin.class);

                startActivity(intent);

            }
        });
        list_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, UserSearchActivity.class);

                startActivity(intent);

            }
        });
        chat_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, userChatFirst.class);
                startActivity(intent);
            }
        });

    }
}