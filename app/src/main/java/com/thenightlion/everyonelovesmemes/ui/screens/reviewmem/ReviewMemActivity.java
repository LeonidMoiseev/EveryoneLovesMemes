package com.thenightlion.everyonelovesmemes.ui.screens.reviewmem;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

        changeStatusBarColor();
        initView();
        getInfoFromExtra();

        closeActivity.setOnClickListener(v -> finish());

        setInfoMem();
    }

    private void setInfoMem() {
        Glide.with(this)
                .load(listMemDto.get(memNumberCell).getPhotoUtl())
                .into(memImage);

        memTitle.setText(listMemDto.get(memNumberCell).getTitle());
        memDescription.setText(listMemDto.get(memNumberCell).getDescription());
        memDateCreated.setText(listMemDto.get(memNumberCell).getCreatedDate());

        if (listMemDto.get(memNumberCell).isFavorite()) {
            memFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_true));
        } else memFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_false));
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

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarkBlue2));
        }
    }
}
