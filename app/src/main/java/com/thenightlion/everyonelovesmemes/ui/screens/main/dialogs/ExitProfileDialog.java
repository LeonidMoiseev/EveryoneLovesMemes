package com.thenightlion.everyonelovesmemes.ui.screens.main.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.ProfileFragmentPresenter;

public class ExitProfileDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private static ExitProfileDialog mInstance;
    private static ProfileFragmentPresenter profileFragmentPresenter;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme)
                .setTitle("Действительно хотите выйти?")
                .setPositiveButton("Выйти", this)
                .setNegativeButton("Отмена", this);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                profileFragmentPresenter.logoutUser();
                break;
            case Dialog.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public static ExitProfileDialog getExitProfileDialog(ProfileFragmentPresenter presenter) {
        profileFragmentPresenter = presenter;
        if (mInstance == null) {
            mInstance = new ExitProfileDialog();
        }
        return mInstance;
    }
}
