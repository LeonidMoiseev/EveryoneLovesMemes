package com.thenightlion.everyonelovesmemes.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thenightlion.everyonelovesmemes.data.model.MemDto;
import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.DashboardFragmentPresenter;
import com.thenightlion.everyonelovesmemes.ui.screens.reviewmem.ReviewMemActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<MemDto> mListMem;
    private List<MemDto> mOriginalValues;
    private List<MemDto> filteredArrList;

    private DashboardFragmentPresenter presenter;

    public MemesAdapter(Context context, List<MemDto> mListMem, DashboardFragmentPresenter presenter) {
        this.context = context;
        this.mListMem = mListMem;
        this.presenter = presenter;
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

        myViewHolder.cellMem.setOnClickListener(v -> {
            Intent startReviewMem = new Intent(context, ReviewMemActivity.class);
            startReviewMem.putExtra("memNumberCell", i);
            startReviewMem.putExtra("listMemDto", (Serializable) mListMem);
            Pair<View, String> p1 = Pair.create(myViewHolder.memImage, "memImage");
            Pair<View, String> p2 = Pair.create(myViewHolder.memTitle, "memTitle");
            Pair<View, String> p3 = Pair.create(myViewHolder.memFavorite, "memFavorite");
            Pair<View, String> p4 = Pair.create(myViewHolder.memShare, "memShare");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4);
            context.startActivity(startReviewMem, options.toBundle());
        });

        myViewHolder.memShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = mListMem.get(i).getTitle() + "\n\n" + mListMem.get(i).getDescription() + "\n" + mListMem.get(i).getPhotoUtl();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(sharingIntent, "Share mem to.."));
        });
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
        CardView cellMem;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            memImage = itemView.findViewById(R.id.memImage);
            memTitle = itemView.findViewById(R.id.memTitle);
            memFavorite = itemView.findViewById(R.id.memFavorite);
            memShare = itemView.findViewById(R.id.memShare);
            cellMem = itemView.findViewById(R.id.cell_mem);
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListMem = (List<MemDto>) results.values;
                notifyDataSetChanged();
                presenter.callMethodHideAndShowStub();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                filteredArrList = new ArrayList<>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<>();
                    mOriginalValues = mListMem;
                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getTitle();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            filteredArrList.add(mOriginalValues.get(i));
                        }
                    }

                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
