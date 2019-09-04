package com.thenightlion.everyonelovesmemes.ui.screens.main.presenters;

import android.support.annotation.NonNull;

import com.thenightlion.everyonelovesmemes.data.api.Service;
import com.thenightlion.everyonelovesmemes.data.model.MemDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragmentPresenter {

    private View view;
    private List<MemDto> memDtoList;
    private boolean firstLoad = true;

    public DashboardFragmentPresenter(View view) {
        this.view = view;
    }

    public void loadMemes() {
        if (firstLoad) {
            view.hideViewStubErrorLoadList();
        }

        Service.getInstance()
                .getMemesApi()
                .getMeme()
                .enqueue(new Callback<List<MemDto>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MemDto>> call, @NonNull Response<List<MemDto>> response) {
                        if (response.body() != null) {
                            memDtoList = response.body();
                            view.initAdapterForRecyclerView(memDtoList);
                            firstLoad = false;
                        } else {
                            view.errorLogin();
                            if (firstLoad) {
                                view.showViewStubErrorLoadList();
                            }
                        }
                        view.progressBarDisabled();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<MemDto>> call, @NonNull Throwable t) {
                        view.errorLogin();
                        if (firstLoad) {
                            view.showViewStubErrorLoadList();
                        }
                        view.progressBarDisabled();
                    }
                });
    }

    public void callMethodHideAndShowStub() {
        view.hideAndShowStub();
    }

    public interface View {
        void initAdapterForRecyclerView(List<MemDto> memDto);
        void progressBarDisabled();
        void showViewStubErrorLoadList();
        void hideViewStubErrorLoadList();
        void errorLogin();
        void hideAndShowStub();
    }
}
