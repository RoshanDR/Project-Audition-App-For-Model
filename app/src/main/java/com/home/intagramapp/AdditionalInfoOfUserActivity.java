package com.home.intagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.intagramapp.Fragment.ProfileFragment;

public class AdditionalInfoOfUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info_of_user);
        final Button btn_home = findViewById(R.id.btn_home);
        final EditText et_mobile = findViewById(R.id.ET_mobie);
        final EditText et_addr = findViewById(R.id.ET_address);
        final EditText et_pin = findViewById(R.id.ET_pincode);
        final EditText et_city = findViewById(R.id.ET_currentcity);
        final EditText et_age = findViewById(R.id.ET_age);
        final EditText et_height = findViewById(R.id.ET_Height);
        final EditText et_weight = findViewById(R.id.ET_Weight);
        final EditText et_race = findViewById(R.id.ET_Race);
        final EditText et_eye = findViewById(R.id.ET_eye);




        // started from here..

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TypeUsers").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mobile = dataSnapshot.child("mobile").getValue(String.class);
                String addr = dataSnapshot.child("addr").getValue(String.class);
                String pin = dataSnapshot.child("pin").getValue(String.class);
                String city = dataSnapshot.child("city").getValue(String.class);
                String age = dataSnapshot.child("age").getValue(String.class);
                String height = dataSnapshot.child("height").getValue(String.class);
                String weight = dataSnapshot.child("weight").getValue(String.class);
                String race = dataSnapshot.child("race").getValue(String.class);
                String eye = dataSnapshot.child("eyeColor").getValue(String.class);


                et_mobile.setText(mobile);
                et_addr.setText(addr);
                et_pin.setText(pin);
                et_city.setText(city);
                et_age.setText(age);
                et_height.setText(height);
                et_weight.setText(weight);
                et_race.setText(race);
                et_eye.setText(eye);

                btn_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(AdditionalInfoOfUserActivity.this, ProfileFragment.class));
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}