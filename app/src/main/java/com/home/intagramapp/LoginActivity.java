package com.home.intagramapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText password, email;
    Button login;
    TextView text_signup;
    FirebaseAuth mauth;
    DatabaseReference reference;
    ProgressDialog pd;
    FirebaseFirestore firebaseFirestore;
    boolean valid = true;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       /* password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
       // text_signup = findViewById(R.id.txt_singnp);
        mauth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /*text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RagisterActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please wait...");
                //pd.show();
                pd.show();
              //  String str_username= username.getText().toString();
               // String str_fullname= fullname.getText().toString();
                String str_email= email.getText().toString();
                String str_password= password.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){

                    Toast.makeText(LoginActivity.this,"All Filds are required",Toast.LENGTH_SHORT).show();

                }else if(str_password.length()<6) {
                    Toast.makeText(LoginActivity.this,"Password must have 6 characters",Toast.LENGTH_SHORT).show();

                }else {

                    mauth.signInWithEmailAndPassword(str_email,str_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){

                               String uid=task.getResult().getUser().getUid();

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
                                      }

                               DatabaseReference  reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(mauth.getCurrentUser().getUid()).child("Utype");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String s="1";
                                        String Utype = dataSnapshot.getValue(String.class);
                                        //Toast.makeText(LoginActivity.this, Utype, Toast.LENGTH_SHORT).show();
                                        if(Utype.equals(s)){
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Intent intent = new Intent(LoginActivity.this, OrgActivity.class);
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

                                  }
                                    }
                                //    @Override
                                  //  public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }else {
                                Toast.makeText(LoginActivity.this,"Authentication Failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                }

            }


        });




    }

             /*    checkFiled(email);

                checkFiled(password);
                //progressBar.setVisibility(View.VISIBLE);
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please wait...");
                pd.show();


               if (valid) {
                    mauth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this, "Login Sucessful...!!!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);


                            //checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Login Failed...!!!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });

        forgetpass=(TextView) findViewById(R.id.forgotpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToForgotActivity();
            }
        });
    }
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = firebaseFirestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSucess:" + documentSnapshot.getData());
                if (documentSnapshot.getString("isAdmin") != null) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                if (documentSnapshot.getString("isUser") != null) {
                    Intent intent = new Intent(LoginActivity.this, UActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                if (documentSnapshot.getString("isOrg") != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }



   private void sendUserToForgotActivity() {
        //Intent forgotIntent = new Intent(Login.this,Forget_pass.class);
        startActivity(new Intent(getApplicationContext(),Forget_pass.class));
        finish();
    }


    private boolean checkFiled(EditText textfiled) {
        if (textfiled.getText().toString().isEmpty()) {
            textfiled.setError("Enter Data");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isUser") != null) {
                        startActivity(new Intent(getApplicationContext(), UActivity.class));
                        finish();
                    }
                    if (documentSnapshot.getString("isOrg") != null) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    if (documentSnapshot.getString("isAdmin") != null) {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });

        }


    }*/
}
}