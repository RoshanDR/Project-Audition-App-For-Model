package com.home.intagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.home.intagramapp.Adapter.MyFotosAdapter;
import com.home.intagramapp.Adapter.MyFotosAdapterForAdmin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Model.Post;
import Model.User;

public class Admin_ProfileOrgActivity extends AppCompatActivity {
    TextView posts,followers, following ,fullname,bio,username ;
    ImageView image_profile,options;
    Button edit_profile;
    FirebaseUser firebaseUser;
    String profileid;
    ImageView my_fotos, saved_fotos;

    RecyclerView recyclerView ;

   // MyFotosAdapter myFotosAdapter;
    MyFotosAdapterForAdmin myFotosAdapter;
    List<Post> postList ;
    FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;

    // ImageAdapterForUesr myFotosAdapter;
    private List<String> mySaves;
    RecyclerView recyclerView_saves ;
    MyFotosAdapter myFotosAdapter_saves;
    List<Post> postList_saves ;
    FirebaseAuth firebaseAuth;

    String userId;
    ImageView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__profile_org);
        progressBar=findViewById(R.id.progress);
        button=findViewById(R.id.fragment3btn);
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ImageActivityForUser.class));
            }
        });*/

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        image_profile = findViewById(R.id.image_profile);
        options = findViewById(R.id.options);
        posts = findViewById(R.id.posts);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.followering);
        fullname = findViewById(R.id.fullname);
        bio = findViewById(R.id.bio);
        username = findViewById(R.id.username);
        edit_profile = findViewById(R.id.edit_profile);
        my_fotos = findViewById(R.id.my_fotos);
        saved_fotos =findViewById(R.id.save_fotos);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myFotosAdapter  = new  MyFotosAdapterForAdmin(getApplicationContext(), postList);

        recyclerView.setAdapter(myFotosAdapter);


        recyclerView_saves = findViewById(R.id.recycler_view_save);
        recyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new GridLayoutManager(getApplicationContext(),3);
        recyclerView_saves.setLayoutManager(linearLayoutManager1);
        postList_saves = new ArrayList<>();
        myFotosAdapter_saves  = new MyFotosAdapter(getApplicationContext(), postList_saves);

        recyclerView_saves.setAdapter(myFotosAdapter_saves);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView_saves.setVisibility(View.GONE);


        userInfo();
       // getFollowers();
        getNrPost();
        myFotos();
        //mysaves();


        if(profileid.equals(firebaseUser.getUid())){
          //  edit_profile.setText("Edit Profile");

        }else {
           // checkFollow();
            saved_fotos.setVisibility(View.GONE);
        }


        /*edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String btn = edit_profile.getText().toString();
                if(btn.equals("Edit Profile")){
                    startActivity(new Intent(getApplicationContext(), EditProfileUser.class));

                }else if(btn.equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);

                    addNotifications();

                }else if(btn.equals("following")){

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).removeValue();


                }
            }
        });


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);

                startActivity(intent);
            }
        });


        my_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_saves.setVisibility(View.GONE);
                saved_fotos.setImageResource(R.drawable.ic_save);
            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FollowersActivity.class);
                intent.putExtra("id", profileid);
                intent.putExtra("title","followers");
                startActivity(intent);
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FollowersActivity.class);
                intent.putExtra("id", profileid);
                intent.putExtra("title","following");
                startActivity(intent);
            }
        });
*/

       /* saved_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(View.GONE);
                recyclerView_saves.setVisibility(View.VISIBLE);

                saved_fotos.setImageResource(R.drawable.ic_save_black);
            }
        });*/
       //return view;
    }

    private  void  addNotifications(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(profileid);

        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("userid",firebaseUser.getUid());
        hashMap.put("text","started following you");
        hashMap.put("postid","");
        hashMap.put("ispost",false);


        reference.push().setValue(hashMap);
    }



    private  void  userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TypeOrg").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(getApplicationContext() == null){

                    return;
                }

                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                bio.setText(user.getBio());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private  void  checkFollow(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(firebaseUser.getUid()).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(profileid).exists()){
                    edit_profile.setText("following");

                }else {
                    edit_profile.setText("follow");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getFollowers(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(profileid).child("followers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //   followers.setText(""+dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(profileid).child("following");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //  following.setText(""+dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void  getNrPost(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        i++;


                    }
                }

                posts.setText(""+i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private  void  myFotos(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Post post = snapshot.getValue(Post.class);

                    if(post.getPublisher().equals(profileid)){
                        postList.add(post);


                    }

                }
                Collections.reverse(postList);
                myFotosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private  void mysaves(){

        mySaves = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    mySaves.add(snapshot.getKey());

                }
                readSaves();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readSaves() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList_saves.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Post  post = snapshot.getValue(Post.class);

                    for(String id : mySaves){
                        if(post.getPostid().equals(id)){

                            postList_saves.add(post);

                        }

                    }

                }

                myFotosAdapter_saves.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
