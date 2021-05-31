package com.home.intagramapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.intagramapp.Adapter.UserAdapterForOrgChat;
import com.home.intagramapp.R;

import java.util.ArrayList;
import java.util.List;

import Model.Chat_Hope;
import Model.User;


public class ChatFragmentForOrg extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapterForOrgChat userAdapter_hope;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;
    private List<String> userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat_fragment_for_org, container, false);

        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        userList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();;
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat_Hope chat_hope=snapshot.getValue(Chat_Hope.class);
                    if (chat_hope.getSender().equals(fuser.getUid())){
                        userList.add(chat_hope.getReceiver());
                    }
                    if (chat_hope.getReceiver().equals(fuser.getUid())){
                        userList.add(chat_hope.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  view;
    }
    private void readChats(){
        mUsers=new ArrayList<>();

        reference =FirebaseDatabase.getInstance().getReference("TypeOrg");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user_hope=snapshot.getValue(User.class);

                    for (String id: userList){
                        if (user_hope.getId().equals(id)){
                            if (mUsers.size() !=0){
                                for (User user_hope1 :mUsers){
                                    if (!user_hope.getId().equals(user_hope1.getId())){
                                        mUsers.add(user_hope);
                                    }
                                }

                            }else {
                                mUsers.add(user_hope);
                            }
                        }
                    }
                }
                userAdapter_hope=new UserAdapterForOrgChat(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter_hope);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
