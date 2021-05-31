package com.home.intagramapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.home.intagramapp.R;

import java.util.List;

import Model.Chat_Hope;

public class MessageAdapterForOrg extends RecyclerView.Adapter<MessageAdapterForOrg.ViewHolder> {
    private Context mContext;
    public  static  final int MSG_TYPE_LEFT=0;
    public  static  final int MSG_TYPE_RIGHT=1;

    private List<Chat_Hope> mChat;
    private String imageurl;
    private FirebaseUser fuser;

    public MessageAdapterForOrg(Context mContext, List<Chat_Hope> mChat, String imageurl){
        this.mChat=mChat;
        this.imageurl=imageurl;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public MessageAdapterForOrg.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);

            return new MessageAdapterForOrg.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);

            return new MessageAdapterForOrg.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterForOrg.ViewHolder holder, int position) {

        Chat_Hope chat_hope= mChat.get(position);
        holder.show_message.setText(chat_hope.getMessage());
        if (imageurl.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message=itemView.findViewById(R.id.show_message);
            profile_image=itemView.findViewById(R.id.profile_image);
            txt_seen=itemView.findViewById(R.id.text_seen);
        }
    }


    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}

