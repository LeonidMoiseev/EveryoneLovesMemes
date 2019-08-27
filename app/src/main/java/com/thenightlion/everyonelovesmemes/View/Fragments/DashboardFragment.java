package com.thenightlion.everyonelovesmemes.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.Adapters.MemesAdapter;
import com.thenightlion.everyonelovesmemes.Api.Service;
import com.thenightlion.everyonelovesmemes.Model.MemDto;
import com.thenightlion.everyonelovesmemes.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private List<MemDto> memDtoList;
    private ProgressBar progressBar;
    private TextView textErrorLoadMemes;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        context = container.getContext();

        initView();
        loadMemes();

        return view;
    }

    private void loadMemes() {
        progressBarEnabled();
        textErrorInvisible();

        Service.getInstance()
                .getMemesApi()
                .getMeme()
                .enqueue(new Callback<List<MemDto>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MemDto>> call, @NonNull Response<List<MemDto>> response) {
                        if (response.body() != null) {
                            memDtoList = response.body();
                            initRecyclerView(memDtoList);
                        } else {
                            errorSnackbar(getString(R.string.error_load_memes));
                            textErrorVisible();
                        }
                        progressBarDisabled();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<MemDto>> call, @NonNull Throwable t) {
                        errorSnackbar(getString(R.string.error_load_memes));
                        textErrorVisible();
                        progressBarDisabled();
                    }
                });
    }

    private void initRecyclerView(List<MemDto> memDto) {
        MemesAdapter memesAdapter = new MemesAdapter(getContext(), memDto);
        memesAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(memesAdapter);
        recyclerView.smoothScrollToPosition(0);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void errorSnackbar(String errorText) {
        Snackbar snackbar = Snackbar.make(view, errorText,
                Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
        snackbar.show();
    }

    private void initView() {
        progressBar = view.findViewById(R.id.progressBarLoadMemes);
        textErrorLoadMemes = view.findViewById(R.id.errorLoadMemesTV);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void progressBarEnabled() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
    }

    private void progressBarDisabled() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void textErrorVisible() {
        textErrorLoadMemes.setVisibility(View.VISIBLE);
    }

    private void textErrorInvisible() {
        textErrorLoadMemes.setVisibility(View.INVISIBLE);
    }
}
