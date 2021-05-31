package com.home.intagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.home.intagramapp.Fragment.ProfileFragment;

import java.util.HashMap;

public class AddInfoActivityOfUser extends AppCompatActivity {

    EditText et_mobile,et_addr,et_pin,et_city,et_age,et_height,et_weight,et_race,et_eye;
    Button save;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_of_user);
        et_mobile = findViewById(R.id.ET_mobie);
        et_addr = findViewById(R.id.ET_address);
        et_pin = findViewById(R.id.ET_pincode);
        et_city = findViewById(R.id.ET_currentcity);
        et_age = findViewById(R.id.ET_age);
        et_height = findViewById(R.id.ET_Height);
        et_weight = findViewById(R.id.ET_Weight);
        et_eye = findViewById(R.id.ET_eye);
        et_race = findViewById(R.id.ET_Race);
        save = findViewById(R.id.btn_save);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("TypeUsers").child(firebaseUser.getUid());



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get all the values
                String mobile = et_mobile.getText().toString();
                String addr = et_addr.getText().toString();
                String pin = et_pin.getText().toString();
                String city = et_city.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                String race = et_race.getText().toString();
                String eye = et_eye.getText().toString();


                updateProfile(mobile,addr,pin,city,age,height,weight,race,eye);
                Toast.makeText(AddInfoActivityOfUser.this,"Saved!",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddInfoActivityOfUser.this, ProfileFragment.class));
            }
        });


    }

    private void updateProfile(String mobile , String addr , String pin , String city , String age,String height,String weight, String race, String eye) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TypeUsers").child(firebaseUser.getUid());
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("mobile",mobile);
        hashMap.put("addr",addr);
        hashMap.put("pin",pin);
        hashMap.put("city",city);
        hashMap.put("age",age);
        hashMap.put("height",height);
        hashMap.put("weight",weight);
        hashMap.put("race",race);
        hashMap.put("eyeColor",eye);

        reference.updateChildren(hashMap);
    }
}