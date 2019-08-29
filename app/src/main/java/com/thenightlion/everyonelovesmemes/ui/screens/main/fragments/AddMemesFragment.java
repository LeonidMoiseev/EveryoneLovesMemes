package com.thenightlion.everyonelovesmemes.ui.screens.main.fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.dialogs.AddImageMemDialog;
import com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.AddMemesFragmentPresenter;

import java.util.Objects;


public class AddMemesFragment extends Fragment implements AddMemesFragmentPresenter.View {

    private final String TEST_IMAGE_URL = "https://cdn1.savepice.ru/uploads/2019/8/29/668128f0b2605527ed3e60d6a803a6b6-full.jpg";
    private View view;

    private TextView createMem;
    private TextInputEditText titleMemET;
    private TextInputEditText descriptionMemET;
    private ImageButton btnLoadImageMem;
    private DialogFragment dialogFragment;
    private ImageView memImage;

    private String titleMem;
    private String descriptionMem;
    private boolean imageLoad = false;

    private AddMemesFragmentPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_memes, container, false);

        presenter = new AddMemesFragmentPresenter(this);
        dialogFragment = AddImageMemDialog.getImageMemDialog();

        initView();
        initButtonsListener();
        addTextChangedListener();
        setImageMem();

        return view;
    }

    private void initButtonsListener() {
        btnLoadImageMem.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            dialogFragment.show(getFragmentManager(), "dialogFragment");
        });

        createMem.setOnClickListener(v -> {
            descriptionMem = Objects.requireNonNull(descriptionMemET.getText()).toString().trim();
            presenter.updateTitleMem(titleMem);
            presenter.updateDescriptionMem(descriptionMem);
            presenter.updatePhotoUtl(TEST_IMAGE_URL);
            presenter.addMemInDatabase();
        });
    }

    private void addTextChangedListener() {
        titleMemET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleMem = Objects.requireNonNull(titleMemET.getText()).toString().trim();
                presenter.checkEnabledButtonCreateMem(titleMem, imageLoad);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setImageMem() {
        Glide.with(Objects.requireNonNull(getActivity()))
                .load(TEST_IMAGE_URL)
                .centerCrop()
                .into(memImage);
        imageLoad = true;
    }

    private void initView() {
        createMem = view.findViewById(R.id.create_mem);
        titleMemET = view.findViewById(R.id.etTitleMem);
        descriptionMemET = view.findViewById(R.id.etDescriptionMem);
        memImage = view.findViewById(R.id.myImageMem);
        btnLoadImageMem = view.findViewById(R.id.loadImageMem);
    }

    @Override
    public void enabledButtonCreateMem() {
        createMem.setEnabled(true);
        createMem.setTextColor(getResources().getColor(R.color.colorBlue));
    }

    @Override
    public void disabledButtonCreateMem() {
        createMem.setEnabled(false);
        createMem.setTextColor(getResources().getColor(R.color.colorBlueDisabled));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
