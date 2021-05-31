package com.home.intagramapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button b1,b2;
    Button login,ragister;
    FirebaseUser firebaseUser;
    /*
       @Override
      protected void onStart() {
           super.onStart();
           firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

           if (firebaseUser != null){

               startActivity(new Intent(StartActivity.this,MainActivity.class));
               finish();
           }
       }
   */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        b1=(Button)findViewById(R.id.create_btn);

        b2=(Button)findViewById(R.id.sign_in);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });
    }

    private void sendUserToRegisterActivity() {
        //Intent registerIntent = new Intent(Welcome_page.this,Register.class);
        Intent intent= new Intent(StartActivity.this,Ragister.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void sendUserToLoginActivity() {
        //Intent registerIntent = new Intent(Welcome_page.this,Register.class);
        Intent intent= new Intent(StartActivity.this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
