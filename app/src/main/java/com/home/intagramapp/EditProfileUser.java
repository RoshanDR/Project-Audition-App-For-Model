package com.home.intagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditProfileUser extends AppCompatActivity {
    Button addinfo, addbio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_user);
        addbio=findViewById(R.id.add_bio);
        addinfo=findViewById(R.id.add_info);
        addbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileUser.this, EditProfileActivity.class);

                startActivity(intent);
            }
        });
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileUser.this, AddInfoActivityOfUser.class);

                startActivity(intent);

            }
        });
    }
}
