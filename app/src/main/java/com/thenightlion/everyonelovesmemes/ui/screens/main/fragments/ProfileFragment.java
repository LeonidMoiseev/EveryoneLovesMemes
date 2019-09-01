package com.thenightlion.everyonelovesmemes.ui.screens.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.utils.SharedPreferencesUtils;
import com.thenightlion.everyonelovesmemes.utils.App;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private View view;
    private TextView usernameProfile;
    private TextView descriptionProfile;
    private RecyclerView recyclerViewProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar_profile);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.profile_menu);
        setHasOptionsMenu(true);

        initView();
        setProfileInformation();

        return view;
    }

    private void setProfileInformation() {
        SharedPreferencesUtils sharedPreferencesUtils = App.getInstance().getSharedPreferencesUtils();
        usernameProfile.setText(sharedPreferencesUtils.getString("username"));
        descriptionProfile.setText(sharedPreferencesUtils.getString("userDescription"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_app:

                break;
            case R.id.exit:

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void initView() {
        usernameProfile = view.findViewById(R.id.username_profile);
        descriptionProfile = view.findViewById(R.id.description_profile);
        recyclerViewProfile = view.findViewById(R.id.recycler_view_profile);
    }
}
