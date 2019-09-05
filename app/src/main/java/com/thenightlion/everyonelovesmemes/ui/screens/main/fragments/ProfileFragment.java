package com.thenightlion.everyonelovesmemes.ui.screens.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfo;
import com.thenightlion.everyonelovesmemes.ui.adapters.MyMemesAdapter;
import com.thenightlion.everyonelovesmemes.ui.screens.authorization.AuthorizationActivity;
import com.thenightlion.everyonelovesmemes.ui.screens.main.dialogs.ExitProfileDialog;
import com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.ProfileFragmentPresenter;

import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment implements ProfileFragmentPresenter.View {

    private View view;
    private TextView usernameProfile;
    private TextView descriptionProfile;
    private RecyclerView recyclerViewProfile;
    private StaggeredGridLayoutManager layoutManager;
    private ProgressBar progressBar;
    private DialogFragment dialogFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProfileFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        presenter = new ProfileFragmentPresenter(this);
        dialogFragment = ExitProfileDialog.getExitProfileDialog(presenter);

        Toolbar toolbar = view.findViewById(R.id.toolbar_profile);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.profile_menu);
        setHasOptionsMenu(true);

        initView();
        pullToRefresh();
        presenter.setProfileInformation();
        presenter.getMyMemesFromDatabase();

        return view;
    }

    private void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getMyMemesFromDatabase());
    }

    @Override
    public void initAdapterForRecyclerView(List<MyMemInfo> myMemInfo) {
        MyMemesAdapter myMemesAdapter = new MyMemesAdapter(getContext(), myMemInfo);
        myMemesAdapter.notifyDataSetChanged();

        recyclerViewProfile.setAdapter(myMemesAdapter);
        recyclerViewProfile.smoothScrollToPosition(0);
        recyclerViewProfile.setLayoutManager(layoutManager);
    }

    @Override
    public void setUsernameProfile(String username) {
        usernameProfile.setText(username);
    }

    @Override
    public void setDescriptionProfile(String description) {
        descriptionProfile.setText(description);
    }

    @Override
    public void progressBarEnabled() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
    }

    @Override
    public void progressBarDisabled() {
        progressBar.setVisibility(View.INVISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void snackbarError(String error) {
        Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorRed));
        snackbar.show();
    }

    @Override
    public void logoutUser() {
        Intent startAuthorizationActivity = new Intent(getActivity(), AuthorizationActivity.class);
        startAuthorizationActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Objects.requireNonNull(getActivity()).startActivity(startAuthorizationActivity);
        getActivity().finish();
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
                assert getFragmentManager() != null;
                dialogFragment.show(getFragmentManager(), "dialogExitProfileFragment");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initView() {
        usernameProfile = view.findViewById(R.id.username_profile);
        descriptionProfile = view.findViewById(R.id.description_profile);
        recyclerViewProfile = view.findViewById(R.id.recycler_view_profile);
        recyclerViewProfile.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        progressBar = view.findViewById(R.id.progressBarProfile);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_content_profile);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorBlue));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
