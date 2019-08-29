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
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        changeStatusBarColor();

        fragmentNavigator = FragmentNavigator.getInstance(this);

        if (savedInstanceState == null) {
            createFragments();
            fragmentNavigator.setFragment(dashboardFragment, "dashboardFragment");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragmentNavigator.showHideFragment(fragmentNavigator.getVisibleFragment());
                fragmentNavigator.setFragment(dashboardFragment, "dashboardFragment");
                return true;
            case R.id.navigation_add_circle:
                fragmentNavigator.showHideFragment(fragmentNavigator.getVisibleFragment());
                fragmentNavigator.setFragment(addMemesFragment, "addMemesFragment");
                return true;
            case R.id.navigation_person:
                fragmentNavigator.showHideFragment(fragmentNavigator.getVisibleFragment());
                fragmentNavigator.setFragment(profileFragment, "profileFragment");
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
        //outState.putString("KEY", "dashboardFragment");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dashboardFragment");
        fragmentNavigator.setFragment(fragment, "dashboardFragment");
        //Toast.makeText(this, savedInstanceState.getString("KEY"), Toast.LENGTH_SHORT).show();
    }
}
