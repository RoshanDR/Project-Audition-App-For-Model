package com.home.intagramapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RagisterActivity extends AppCompatActivity {

    EditText username ,phone_no,password,email;
    Button ragister;
    TextView text_login;
    FirebaseAuth mauth;
    DatabaseReference reference;
    CheckBox isuser,isorg;
    ProgressBar progressBar;
    ProgressDialog pd;
    boolean valid=true;

    FirebaseFirestore firebaseFirestore;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);

        username = findViewById(R.id.username);
        phone_no = findViewById(R.id.et_no);
        password = findViewById(R.id.et_pass);
        email = findViewById(R.id.email);
        ragister = findViewById(R.id.register_btn);
        text_login = findViewById(R.id.txt_login);

        firebaseFirestore=FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RagisterActivity.this,LoginActivity.class));
            }
        });
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        isorg=(CheckBox)findViewById(R.id.check_org);
        isuser=(CheckBox)findViewById(R.id.check_user);

        isuser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()){
                    isorg.setChecked(false);
                }
            }
        });
        isorg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()){
                    isuser.setChecked(false);
                }
            }
        });

        ragister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd = new ProgressDialog(RagisterActivity.this);
                pd.setMessage("Please wait...");
                pd.show();
              String str_username= username.getText().toString();
                String str_fullname= phone_no.getText().toString();
                String str_email= email.getText().toString();
                String str_password= password.getText().toString();
                String str_check1=isorg.getText().toString();
                String str_check2=isuser.getText().toString();
                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(
                        str_fullname) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){

                    Toast.makeText(RagisterActivity.this,"All Filds are required",Toast.LENGTH_SHORT).show();

                }else if(str_password.length()<6) {
                    Toast.makeText(RagisterActivity.this,"Password must have 6 characters",Toast.LENGTH_SHORT).show();


                }
                else if(!(isuser.isChecked() || isorg.isChecked())){
                    Toast.makeText(RagisterActivity.this, "Select The Account Type....!!!!", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    register(str_username,str_fullname,str_email,str_password);

                }

                }


        });




    }

    private  void register (final String username , final String fullname, final String email , String password){

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RagisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){


                       FirebaseUser firebaseUser = mauth.getCurrentUser();
                       String userid  = firebaseUser.getUid();
                       reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                       HashMap<String,Object> hashMap = new HashMap<>();

                       hashMap.put("id",userid);
                       hashMap.put("username",username);
                       hashMap.put("fullname",fullname);
                       hashMap.put("bio","");
                       hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/project-e8b81.appspot.com/o/placeholder.png?alt=media&token=f1d0608d-b8da-4512-aa98-867a63adb95a");


                       if (isuser.isChecked()){
                           hashMap.put("Utype","1");
                       }
                       if (isorg.isChecked()){
                           hashMap.put("Utype","0");
                       }

                       reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {

                               if(task.isSuccessful()){

                                   // progressDialog.dismiss();
                                   Intent intent = new Intent(RagisterActivity.this ,LoginActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                   startActivity(intent);
                               }
                           }
                       });



                }else {
                   // progressDialog.dismiss();
                    Toast.makeText(RagisterActivity.this,"You can not register with this email and password",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
              /*  checkFiled(username);
                checkFiled(email);
                checkFiled(phone_no);
                checkFiled(password);
                //checkbox validation
                if(!(isuser.isChecked() || isorg.isChecked())){
                    Toast.makeText(RagisterActivity.this, "Select The Account Type....!!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                progressDialog.show();

                if(valid){
                    mauth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user=mauth.getCurrentUser();
                            Toast.makeText(RagisterActivity.this, "Account Created....!!!!", Toast.LENGTH_SHORT).show();

                            DocumentReference df=firebaseFirestore.collection("Users").document(user.getUid());
                            Map<String,Object> userinfo=new HashMap<>();
                            userinfo.put("Name",username.getText().toString());
                            userinfo.put("UserEmail",email.getText().toString());
                            userinfo.put("Phone number",phone_no.getText().toString());

                            // Access level check
                            if (isuser.isChecked()){
                                userinfo.put("isUser","1");
                            }
                            if (isorg.isChecked()){
                                userinfo.put("isOrg","1");
                            }

                            df.set(userinfo);
                            progressBar.setVisibility(View.INVISIBLE);

                            Intent intent= new Intent(RagisterActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RagisterActivity.this, "Failed To Create Account...!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



    }

    private boolean checkFiled(EditText textfiled)
    {

        if(textfiled.getText().toString().isEmpty()){
            textfiled.setError("Enter Data");
            valid=false;
        }else {
            valid=true;
        }
        return valid;
    }

*/


}
