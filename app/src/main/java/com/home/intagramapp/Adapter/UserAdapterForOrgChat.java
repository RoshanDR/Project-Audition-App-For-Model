package com.home.intagramapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.home.intagramapp.MessaheActivityForUser;
import com.home.intagramapp.R;

import java.util.List;

import Model.User;

public class UserAdapterForOrgChat extends RecyclerView.Adapter<UserAdapterForOrgChat.ViewHolder> {

    private Context mContext ;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;


    public UserAdapterForOrgChat(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.user_item_hope,parent,false);
        return  new UserAdapterForOrgChat.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user=mUsers.get(position);
        holder.username.setText(user.getUsername());
        Glide.with(mContext).load(user.getImageurl()).into(holder.profile_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, MessaheActivityForUser.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        public ViewHolder(View view){
            super(view);
            username=view.findViewById(R.id.username);
            profile_image=view.findViewById(R.id.profile_image);
        }
}
}
