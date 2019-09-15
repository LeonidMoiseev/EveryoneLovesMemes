package com.thenightlion.everyonelovesmemes.ui.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.thenightlion.everyonelovesmemes.R;

import java.util.List;

class FragmentNavigator {

    private Context context;

    FragmentNavigator(Context context) {
        this.context = context;
    }

    void setFragment(Fragment targetFragment, String tag) {
        Fragment fragmentTarget = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentTarget == null) {
            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_main, targetFragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            showFragment(targetFragment);
        }
    }

    private void hideFragment(Fragment currentFragment) {
        FragmentTransaction ftHide = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftHide.hide(currentFragment);
        ftHide.commit();
    }

    private void showFragment(Fragment targetFragment) {
        FragmentTransaction ftShow = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftShow.show(targetFragment);
        ftShow.commit();
    }

    void checkHideAndShowFragment(Fragment targetFragment, String tag) {
        Fragment currentFragment = getVisibleFragment();

        if (currentFragment != targetFragment) {
            hideFragment(currentFragment);
            setFragment(targetFragment, tag);
        }
    }

    Fragment getVisibleFragment() {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }
}
