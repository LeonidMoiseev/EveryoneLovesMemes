package com.thenightlion.everyonelovesmemes.ui.screens.main;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.fragments.AddMemesFragment;
import com.thenightlion.everyonelovesmemes.ui.screens.main.fragments.DashboardFragment;
import com.thenightlion.everyonelovesmemes.ui.screens.main.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private DashboardFragment dashboardFragment;
    private AddMemesFragment addMemesFragment;
    private ProfileFragment profileFragment;
    private FragmentNavigator fragmentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        changeStatusBarColor();

        fragmentNavigator = new FragmentNavigator(this);

        if (savedInstanceState == null) {
            createFragments();
            fragmentNavigator.setFragment(dashboardFragment, "dashboardFragment");
        } else {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentByTag(savedInstanceState.getString("KEY"));
            fragmentNavigator.setFragment(fragment, savedInstanceState.getString("KEY"));
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragmentNavigator.checkHideAndShowFragment(dashboardFragment, "dashboardFragment");
                return true;
            case R.id.navigation_add_circle:
                fragmentNavigator.checkHideAndShowFragment(addMemesFragment, "addMemesFragment");
                return true;
            case R.id.navigation_person:
                fragmentNavigator.checkHideAndShowFragment(profileFragment, "profileFragment");
                return true;
        }
        return true;
    };

    private void createFragments() {
        dashboardFragment = new DashboardFragment();
        addMemesFragment = new AddMemesFragment();
        profileFragment = new ProfileFragment();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarkBlue2));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("KEY", fragmentNavigator.getVisibleFragment().getTag());
    }

    private void initView() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
