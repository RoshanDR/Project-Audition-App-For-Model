package com.home.intagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.home.intagramapp.Fragment.ChatFragment;
import com.home.intagramapp.Fragment.NotificationFragment;
import com.home.intagramapp.Fragment.OrgHomeFragment;
import com.home.intagramapp.Fragment.OrgProfileFragment;
import com.home.intagramapp.Fragment.SearchFragment;

public class OrgActivity extends AppCompatActivity {

    Button button;
    FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView ;
    Fragment selectedFagrament = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);
      /*  button=findViewById(R.id.lbutton);
        firebaseAuth= FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(OrgActivity.this,LoginActivity.class));
            }
        });*/
        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemReselectedListener(navigationItemSelectedListner);

        Bundle intent = getIntent().getExtras();
        if(intent != null){

            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new OrgProfileFragment()).commit();

        }else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new OrgHomeFragment()).commit();

        }





    }
    private  BottomNavigationView.OnNavigationItemReselectedListener navigationItemSelectedListner =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                       case R.id.nav_chat:
                           selectedFagrament = null;
                           startActivity(new Intent(OrgActivity.this, MessageActivityForAdmin.class));
                           break;
                        case R.id.nav_search:
                            selectedFagrament = new SearchFragment();
                            break;
                        case R.id.nav_post:
                            selectedFagrament = null;
                            startActivity(new Intent(OrgActivity.this, OrgPostActivity.class));
                            break;
                        case R.id.nav_heart:
                          selectedFagrament = new NotificationFragment();
                        break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();

                            selectedFagrament = new OrgProfileFragment();
                            break;
                    }



                    if (selectedFagrament != null) {

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFagrament).commit();

                    }
                    return ;
                }

            };
    }
