package com.thenightlion.everyonelovesmemes.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class PermissionsUtils {
    private Context context;

    PermissionsUtils(Context context) {
        this.context = context;
    }

    public boolean checkPermissionReadExternalStorage() {
        int permissionStatus = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return permissionStatus == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionCamera() {
        int permissionStatus = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);

        return permissionStatus == PackageManager.PERMISSION_GRANTED;
    }
}
