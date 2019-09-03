package com.thenightlion.everyonelovesmemes.ui.screens.main.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.AddMemesFragmentPresenter;

public class AddImageMemDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private static AddImageMemDialog mInstance;
    private static AddMemesFragmentPresenter addMemesFragmentPresenter;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme)
                .setTitle("Выбрать фото")
                .setPositiveButton(R.string.from_gallery, this)
                .setNegativeButton(R.string.make_photo, this);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                addMemesFragmentPresenter.checkExistPermissionReadExternalStorage();
                break;
            case Dialog.BUTTON_NEGATIVE:
                addMemesFragmentPresenter.checkPermissionCamera();
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public static AddImageMemDialog getImageMemDialog(AddMemesFragmentPresenter presenter) {
        addMemesFragmentPresenter = presenter;
        if (mInstance == null) {
            mInstance = new AddImageMemDialog();
        }
        return mInstance;
    }
}
