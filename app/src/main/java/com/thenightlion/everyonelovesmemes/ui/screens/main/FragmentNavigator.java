package com.thenightlion.everyonelovesmemes.ui.screens.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.thenightlion.everyonelovesmemes.R;

import java.util.List;

public class FragmentNavigator {

    @SuppressLint("StaticFieldLeak")
    private static FragmentNavigator mInstance;
    private Context context;

    private FragmentNavigator(Context context) {
        this.context = context;
    }

    public void setFragment(Fragment fragment, String tag) {
        Fragment fragmentTarget = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentTarget == null) {
            FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_main, fragment, tag);
            fragmentTransaction.commit();
        } else showHideFragment(fragment);
    }

    public void showHideFragment(final Fragment fragment){
        FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);

        if (fragment.isHidden()) {
            ft.show(fragment);
        } else {
            ft.hide(fragment);
        }

        ft.commit();
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public static FragmentNavigator getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FragmentNavigator(context);
        }
        return mInstance;
    }
}
