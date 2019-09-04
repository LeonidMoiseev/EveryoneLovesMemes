package com.thenightlion.everyonelovesmemes.ui.screens.main.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
    private StaggeredGridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MemesAdapter memesAdapter;

    private RelativeLayout toolbarDashboard;
    private ImageButton btnSearch;

    private RelativeLayout toolbarSearch;
    private ImageButton btnSearchOnBack;
    private ImageButton btnSearchClearText;
    private EditText searchET;

    private ViewStub viewStubEmptySearch;
    private ViewStub viewStubErrorLoadList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        presenter = new DashboardFragmentPresenter(this);

        initView();
        setListeners();

        viewStubEmptySearch.inflate();
        viewStubErrorLoadList.inflate();
        viewStubEmptySearch.setVisibility(View.INVISIBLE);
        viewStubErrorLoadList.setVisibility(View.INVISIBLE);

        presenter.loadMemes();
        pullToRefresh();
        progressBarEnabled();

        return view;
    }

    private void setSearchOnChangeTextListener() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                memesAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadMemes());
    }

    private void setButtonSearchListener() {
        btnSearch.setOnClickListener(v -> {
            toolbarDashboard.setVisibility(View.GONE);
            toolbarSearch.setVisibility(View.VISIBLE);
            showKeyboard();
        });
    }

    private void setButtonSearchOnBackListener() {
        btnSearchOnBack.setOnClickListener(v -> {
            toolbarSearch.setVisibility(View.GONE);
            toolbarDashboard.setVisibility(View.VISIBLE);
            closeKeyboard();
        });
    }

    private void setButtonSearchClearTextListener() {
        btnSearchClearText.setOnClickListener(v -> searchET.getText().clear());
    }

    public void showKeyboard(){
        searchET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager)
                Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        searchET.getText().clear();

        InputMethodManager imm = (InputMethodManager)
                Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void hideAndShowStub() {
        if (memesAdapter.getItemCount() == 0) {
            viewStubEmptySearch.setVisibility(View.VISIBLE);
        } else viewStubEmptySearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initAdapterForRecyclerView(List<MemDto> memDto) {
        memesAdapter = new MemesAdapter(getContext(), memDto, presenter);
        memesAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(memesAdapter);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void errorLogin() {
        Snackbar snackbar = Snackbar.make(view, getString(R.string.error_load_memes),
                Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorRed));
        snackbar.show();
    }

    public void progressBarEnabled() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressBarDisabled() {
        progressBar.setVisibility(View.INVISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showViewStubErrorLoadList() {
        viewStubErrorLoadList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewStubErrorLoadList() {
        viewStubErrorLoadList.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        progressBar = view.findViewById(R.id.progressBarLoadMemes);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_content);
        viewStubEmptySearch = view.findViewById(R.id.view_stub_empty_search);
        viewStubErrorLoadList = view.findViewById(R.id.view_stub_error_load_list);
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearchOnBack = view.findViewById(R.id.btn_back);
        btnSearchClearText = view.findViewById(R.id.clear_text);
        searchET = view.findViewById(R.id.search_ET);
        toolbarDashboard = view.findViewById(R.id.toolbar_dashboard);
        toolbarSearch = view.findViewById(R.id.toolbar_search);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorBlue));
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void setListeners() {
        setButtonSearchListener();
        setButtonSearchOnBackListener();
        setSearchOnChangeTextListener();
        setButtonSearchClearTextListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        //presenter.loadMemes();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
