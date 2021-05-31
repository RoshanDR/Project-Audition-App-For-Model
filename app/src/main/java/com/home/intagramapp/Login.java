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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    TextView forgetpass;
    EditText email,password;
    boolean valid=true;
    Button login;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        email=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.pass);
        login=(Button)findViewById(R.id.login_btn);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait.......");

        firebaseAuth=FirebaseAuth.getInstance();
        // firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        forgetpass=(TextView) findViewById(R.id.forgotpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToForgotActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog
                        pd = new ProgressDialog(Login.this);
                pd.setMessage("Please wait...");
                //pd.show();
                pd.show();
                //  String str_username= username.getText().toString();
                // String str_fullname= fullname.getText().toString();
                String str_email= email.getText().toString();
                String str_password= password.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){

                    Toast.makeText(Login.this,"All Filds are required",Toast.LENGTH_SHORT).show();

                }else if(str_password.length()<6) {
                    Toast.makeText(Login.this,"Password must have 6 characters",Toast.LENGTH_SHORT).show();

                }else {

                    firebaseAuth.signInWithEmailAndPassword(str_email,str_password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){

                              /*  String uid=task.getResult().getUser().getUid();

                              FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                              firebaseDatabase.getReference().child("Users").child(uid).child("Utype").addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      String Utype = dataSnapshot.getValue(String.class);
                                      if (Utype == "1") {
                                          Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                                          startActivity(intent);
                                      }
                                      else{
                                          Intent intent = new Intent(LoginActivity.this, PostActivity.class);
                                          startActivity(intent);
                                      }*/

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TypeOrg")
                                        .child(firebaseAuth.getCurrentUser().getUid()).child("Utype");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String s="2";
                                        String Utype = dataSnapshot.getValue(String.class);
                                        //Toast.makeText(Login.this, Utype, Toast.LENGTH_SHORT).show();
                                        if(Utype!=null){
                                            if(Utype.equals(s)){
                                                Intent intent = new Intent(Login.this, AdminActivity.class);
                                                startActivity(intent);}else {
                                                Intent intent = new Intent(Login.this, OrgActivity.class);
                                                startActivity(intent);

                                            }



                                        }else{
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);
                                        }

                               /* DatabaseReference  reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(mauth.getCurrentUser().getUid());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        pd.dismiss();

                                        Intent intent = new Intent(LoginActivity.this,StartActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                  }*/
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }else {
                                Toast.makeText(Login.this,"Authentication Failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                }

            }


        });




    }

    private void sendUserToForgotActivity() {
        //Intent forgotIntent = new Intent(Login.this,Forget_pass.class);
        startActivity(new Intent(getApplicationContext(),Forget_pass.class));
        finish();
    }

}

