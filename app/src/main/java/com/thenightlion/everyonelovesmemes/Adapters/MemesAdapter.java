package com.thenightlion.everyonelovesmemes.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thenightlion.everyonelovesmemes.Model.MemDto;
import com.thenightlion.everyonelovesmemes.R;

import java.util.List;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.MyViewHolder> {

    private Context context;
    private List<MemDto> mListMem;
    /*private int memId;
    private String memTitle;
    private String memDescription;
    private boolean isFavorite;
    private int createdDate;*/


    public MemesAdapter(Context context, List<MemDto> mListMem) {
        this.context = context;
        this.mListMem = mListMem;
    }

    @NonNull
    @Override
    public MemesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.meme_cell, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemesAdapter.MyViewHolder myViewHolder, int i) {

        Glide.with(context)
                .load(mListMem.get(i).getPhotoUtl())
                .into(myViewHolder.memImage);

        myViewHolder.memTitle.setText(mListMem.get(i).getTitle());
        if (mListMem.get(i).isFavorite()) {
            myViewHolder.memFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_true));
        } else myViewHolder.memFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_false));
    }

    @Override
    public int getItemCount() {
        return mListMem.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView memImage;
        TextView memTitle;
        ImageButton memFavorite;
        ImageButton memShare;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            memImage = itemView.findViewById(R.id.memImage);
            memTitle = itemView.findViewById(R.id.memTitle);
            memFavorite = itemView.findViewById(R.id.memFavorite);
            memShare = itemView.findViewById(R.id.memShare);
        }
    }
}
