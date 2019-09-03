package com.thenightlion.everyonelovesmemes.ui.screens.main.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.thenightlion.everyonelovesmemes.utils.App;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.AddMemesFragmentPresenter.CAMERA_REQUEST;
import static com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.AddMemesFragmentPresenter.PICK_IMAGE_REQUEST;
import static com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.AddMemesFragmentPresenter.REQUEST_CODE_PERMISSION_CAMERA;
import static com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.AddMemesFragmentPresenter.REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE;


public class AddMemesFragment extends Fragment implements AddMemesFragmentPresenter.View {

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
    String uri;

    private AddMemesFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_memes, container, false);

        presenter = new AddMemesFragmentPresenter(this);
        dialogFragment = AddImageMemDialog.getImageMemDialog(presenter);

        initView();
        initButtonsListener();
        addTextChangedListener();

        return view;
    }

    private void initButtonsListener() {
        btnLoadImageMem.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            dialogFragment.show(getFragmentManager(), "dialogAddMemesFragment");
        });

        createMem.setOnClickListener(v -> {
            descriptionMem = Objects.requireNonNull(descriptionMemET.getText()).toString().trim();
            presenter.updateTitleMem(titleMem);
            presenter.updateDescriptionMem(descriptionMem);
            presenter.addMemInDatabase();
            showSnackbarMemCreate();
        });
    }

    private void showSnackbarMemCreate() {
        Snackbar snackbar = Snackbar.make(view, "Мем Создан", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()),
                R.color.colorDarkBlue2));
        snackbar.show();
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

    @Override
    public void startActivityChooseImage(Intent intent) {
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void startActivityCamera(Intent intent) {
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    uri = App.getInstance().getOpenGalleryAndCameraUtils().getImageFilePath(data);
                    loadImage();
                    break;

                case CAMERA_REQUEST:
                    uri = App.getInstance().getOpenGalleryAndCameraUtils().getPhotoFilePath();
                    loadImage();
                    break;
            }
    }

    private void loadImage() {
        Glide.with(this)
                .load(uri)
                .into(memImage);
        imageLoad = true;
        presenter.updatePhotoUri(uri);
    }

    @Override
    public void startRequestPermission(String[] permission, int code) {
        requestPermissions(permission, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityChooseImage(App.getInstance().getOpenGalleryAndCameraUtils().getGalleryIntent());
                }
                break;
            case REQUEST_CODE_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityCamera(App.getInstance().getOpenGalleryAndCameraUtils().getCameraIntent());
                }
                break;
        }
    }
}
