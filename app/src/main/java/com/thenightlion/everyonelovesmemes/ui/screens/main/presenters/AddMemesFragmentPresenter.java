package com.thenightlion.everyonelovesmemes.ui.screens.main.presenters;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;

import com.thenightlion.everyonelovesmemes.data.model.MemDto;
import com.thenightlion.everyonelovesmemes.utils.App;
import com.thenightlion.everyonelovesmemes.data.room.AppDatabase;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfo;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfoDao;

public class AddMemesFragmentPresenter {

    private MemDto memDto;
    private View view;
    public static final int PICK_IMAGE_REQUEST = 33;
    public static final int CAMERA_REQUEST = 34;
    public static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 2;

    public AddMemesFragmentPresenter(View view) {
        this.memDto = new MemDto();
        this.view = view;
    }

    public void updateTitleMem(String title) {
        memDto.setTitle(title);
    }

    public void updateDescriptionMem(String description) {
        memDto.setDescription(description);
    }

    public void updatePhotoUri(String uri) {
        memDto.setPhotoUtl(uri);
    }

    public void addMemInDatabase() {
        AppDatabase db = App.getInstance().getDatabase();
        MyMemInfoDao myMemInfoDao = db.myMemInfoDao();
        MyMemInfo myMemInfo = new MyMemInfo();
        myMemInfo.setTitle(memDto.getTitle());
        myMemInfo.setDescription(memDto.getDescription());
        myMemInfo.setPhotoUtl(memDto.getPhotoUtl());
        myMemInfo.setCreatedDate(System.currentTimeMillis());
        myMemInfoDao.insert(myMemInfo);
    }

    public void checkEnabledButtonCreateMem(String title, boolean imageLoad) {
        if (!TextUtils.isEmpty(title) && imageLoad) {
            view.enabledButtonCreateMem();
        } else {
            view.disabledButtonCreateMem();
        }
    }

    public void checkExistPermissionReadExternalStorage() {
        if (App.getInstance().getPermissionsUtils().checkPermissionReadExternalStorage()) {

            view.startActivityChooseImage(App.getInstance().getOpenGalleryAndCameraUtils().getGalleryIntent());

        } else view.startRequestPermission(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
    }

    public void checkPermissionCamera() {
        if (App.getInstance().getPermissionsUtils().checkPermissionCamera()) {

            view.startActivityCamera(App.getInstance().getOpenGalleryAndCameraUtils().getCameraIntent());

        } else view.startRequestPermission(new String[] {Manifest.permission.CAMERA},
                REQUEST_CODE_PERMISSION_CAMERA);
    }


    public interface View {
        void enabledButtonCreateMem();
        void disabledButtonCreateMem();
        void startActivityChooseImage(Intent intent);
        void startActivityCamera(Intent intent);
        void startRequestPermission(String[] permission, int requestCode);
    }
}
