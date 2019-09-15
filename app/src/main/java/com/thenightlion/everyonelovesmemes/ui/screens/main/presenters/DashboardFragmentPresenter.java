package com.thenightlion.everyonelovesmemes.ui.screens.main.presenters;

import com.thenightlion.everyonelovesmemes.data.api.Service;
import com.thenightlion.everyonelovesmemes.data.model.MemDto;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DashboardFragmentPresenter {

    private View view;
    private boolean firstLoad = true;

    public DashboardFragmentPresenter(View view) {
        this.view = view;
    }

    public void loadMemes() {
        if (firstLoad) {
            view.hideViewStubErrorLoadList();
        }

        Service.getInstance().getMemesApi().getMeme()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MemDto>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MemDto> memDto) {
                        view.initAdapterForRecyclerView(memDto);
                        firstLoad = false;
                        view.progressBarDisabled();
                    }

                    @Override
                    public void onError(Throwable e) {
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
