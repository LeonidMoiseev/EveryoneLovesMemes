package com.thenightlion.everyonelovesmemes.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OpenGalleryAndCameraUtils {

    private Context context;
    private String photoFilePath;

    OpenGalleryAndCameraUtils(Context context) {
        this.context = context;
    }

    private File createPhotoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoFilePath = image.getAbsolutePath();
        return image;
    }

    private String createImageFilePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        assert selectedImage != null;
        Cursor cursor = context.getContentResolver().
                query(selectedImage, filePathColumn, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        return imgDecodableString;
    }

    public Intent getCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try { photoFile = createPhotoFile(); } catch (IOException ex) {
            Log.d("IOexception", "photo IOexception");
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(context,
                    "com.example.android.provider", photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            return cameraIntent;
        } else return null;
    }

    public Intent getGalleryIntent(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        return galleryIntent;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public String getImageFilePath(Intent data) {
        return createImageFilePath(data);
    }
}
