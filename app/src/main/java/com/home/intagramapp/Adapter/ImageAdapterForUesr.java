package com.home.intagramapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.intagramapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Post;

public class ImageAdapterForUesr extends RecyclerView.Adapter <ImageAdapterForUesr.ImageViewHolder>{

    private Context mcontext;
    private List<Post> mposts;
    public ImageAdapterForUesr(Context context,List<Post> posts){
        mcontext=context;
        mposts=posts;
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(mcontext).inflate(R.layout.image_item_user,parent,false);
       return  new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
Post postCurrent=mposts.get(position);
holder.text_des.setText(postCurrent.getDescription());
        Picasso.with(mcontext).load(postCurrent.getPostimage()).fit().centerCrop().into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mposts.size();
    }

    public  class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView text_des;
        public ImageView imageView;
        public ImageViewHolder(View itemView)
        {
            super(itemView);
            text_des=itemView.findViewById(R.id.text_view_user);
            imageView=itemView.findViewById(R.id.image_view_user);
        }

    }
}
