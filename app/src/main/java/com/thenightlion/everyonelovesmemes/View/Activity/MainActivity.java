package com.thenightlion.everyonelovesmemes.View.Activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.View.Fragments.AddMemesFragment;
import com.thenightlion.everyonelovesmemes.View.Fragments.DashboardFragment;
import com.thenightlion.everyonelovesmemes.View.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private DashboardFragment dashboardFragment;
    private AddMemesFragment addMemesFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarkBlue2));
        }

        dashboardFragment = new DashboardFragment();
        addMemesFragment = new AddMemesFragment();
        profileFragment = new ProfileFragment();

        setFragment(dashboardFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                setFragment(dashboardFragment);
                return true;
            case R.id.navigation_add_circle:
                setFragment(addMemesFragment);
                return true;
            case R.id.navigation_person:
                setFragment(profileFragment);
                return true;
        }
        return true;
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
    }
}
