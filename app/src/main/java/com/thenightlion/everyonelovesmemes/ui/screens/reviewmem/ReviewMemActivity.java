package com.thenightlion.everyonelovesmemes.ui.screens.reviewmem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thenightlion.everyonelovesmemes.data.model.MemDto;
import com.thenightlion.everyonelovesmemes.R;

import java.util.List;

public class ReviewMemActivity extends AppCompatActivity {

    private ImageButton closeActivity;
    private ImageButton shareMem;
    private ImageButton memFavorite;

    private ImageView memImage;

    private TextView memTitle;
    private TextView memDateCreated;
    private TextView memDescription;

    private int memNumberCell;
    private List<MemDto> listMemDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_mem);
        postponeEnterTransition();

        changeStatusBarColor();
        initView();
        getInfoFromExtra();
        initShareButtonListener();

        closeActivity.setOnClickListener(v -> onBackPressed());

        setInfoMem();
    }

    private void setInfoMem() {
        Glide.with(this)
                .load(listMemDto.get(memNumberCell).getPhotoUtl())
                .listener(new RequestListener<Drawable>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                  scheduleStartPostponedTransition(memImage);
                                  return false;
                              }
                          })
                .into(memImage);

        memTitle.setText(listMemDto.get(memNumberCell).getTitle());
        memDescription.setText(listMemDto.get(memNumberCell).getDescription());
        memDateCreated.setText(listMemDto.get(memNumberCell).getCreatedDate());

        if (listMemDto.get(memNumberCell).isFavorite()) {
            memFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_true));
        } else memFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_false));
    }

    private void scheduleStartPostponedTransition(final ImageView imageView) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    private void getInfoFromExtra() {
        Intent intent = getIntent();
        memNumberCell = intent.getIntExtra("memNumberCell", 0);
        listMemDto = (List<MemDto>) intent.getSerializableExtra("listMemDto");
    }

    private void initView() {
        closeActivity = findViewById(R.id.close_review_mem);
        shareMem = findViewById(R.id.btn_share);
        memFavorite = findViewById(R.id.mem_favorite);
        memImage = findViewById(R.id.mem_image);
        memTitle = findViewById(R.id.mem_title);
        memDateCreated = findViewById(R.id.mem_date_created);
        memDescription = findViewById(R.id.mem_description);
    }

    private void initShareButtonListener() {
        shareMem.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = listMemDto.get(memNumberCell).getTitle() + "\n\n" + listMemDto.get(memNumberCell).getDescription() + "\n" + listMemDto.get(memNumberCell).getPhotoUtl();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share mem to.."));
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarkBlue2));
        }
    }
}
