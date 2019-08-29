package com.thenightlion.everyonelovesmemes.ui.screens.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.ui.adapters.MemesAdapter;
import com.thenightlion.everyonelovesmemes.data.model.MemDto;
import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.presenters.DashboardFragmentPresenter;

import java.util.List;
import java.util.Objects;

public class DashboardFragment extends Fragment implements DashboardFragmentPresenter.View {
    private DashboardFragmentPresenter presenter;

    private View view;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textErrorLoadMemes;
    private StaggeredGridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        presenter = new DashboardFragmentPresenter(this);

        initView();
        pullToRefresh();

        progressBarEnabled();

        return view;
    }

    private void initView() {
        progressBar = view.findViewById(R.id.progressBarLoadMemes);
        textErrorLoadMemes = view.findViewById(R.id.errorLoadMemesTV);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_content);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorBlue));
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadMemes());
    }

    public void progressBarEnabled() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void initRecyclerView(List<MemDto> memDto) {
        MemesAdapter memesAdapter = new MemesAdapter(getContext(), memDto);
        memesAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(memesAdapter);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadMemes();
    }

    @Override
    public void errorLogin() {
        Snackbar snackbar = Snackbar.make(view, getString(R.string.error_load_memes),
                Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorRed));
        snackbar.show();
    }

    @Override
    public void progressBarDisabled() {
        progressBar.setVisibility(View.INVISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void textErrorVisible() {
        textErrorLoadMemes.setVisibility(View.VISIBLE);
    }

    @Override
    public void textErrorInvisible() {
        textErrorLoadMemes.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
