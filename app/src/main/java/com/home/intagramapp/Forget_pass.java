package com.home.intagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_pass extends AppCompatActivity {

    EditText textView;
    private FirebaseAuth firebaseAuth;
    Button reset;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        reset= (Button)findViewById(R.id.reset);
        textView= (EditText) findViewById(R.id.sendemail);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait.......");
        firebaseAuth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=textView.getText().toString().trim();
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(Forget_pass.this, "Please Enter your valid Email Address", Toast.LENGTH_SHORT).show();
                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(Forget_pass.this, "Check your Email Account,if you want to reset your password..", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent= new Intent(Forget_pass.this,Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                String message=task.getException().getMessage();
                                Toast.makeText(Forget_pass.this, "Error occured" + message , Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });
                }
            }
        });
    }
}
