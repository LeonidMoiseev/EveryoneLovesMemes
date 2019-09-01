package com.thenightlion.everyonelovesmemes.ui.screens.main.presenters;

import android.support.annotation.NonNull;

import com.thenightlion.everyonelovesmemes.data.api.Service;
import com.thenightlion.everyonelovesmemes.data.model.ErrorResponseDto;
import com.thenightlion.everyonelovesmemes.data.room.AppDatabase;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfo;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfoDao;
import com.thenightlion.everyonelovesmemes.utils.App;
import com.thenightlion.everyonelovesmemes.utils.SharedPreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragmentPresenter {
    private View view;

    public ProfileFragmentPresenter(View view) {
        this.view = view;
    }

    public void getMyMemesFromDatabase() {
        view.progressBarEnabled();
        AppDatabase db = App.getInstance().getDatabase();
        MyMemInfoDao myMemInfoDao = db.myMemInfoDao();
        List<MyMemInfo> myMemes = myMemInfoDao.getAll();

        view.initAdapterForRecyclerView(myMemes);
        view.progressBarDisabled();
    }

    public void setProfileInformation() {
        SharedPreferencesUtils sharedPreferencesUtils = App.getInstance().getSharedPreferencesUtils();
        view.setUsernameProfile(sharedPreferencesUtils.getString("username"));
        view.setDescriptionProfile(sharedPreferencesUtils.getString("userDescription"));
    }

    public void logoutUser() {
        view.progressBarEnabled();

        Service.getInstance()
                .getLogoutApi()
                .logout()
                .enqueue(new Callback<ErrorResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<ErrorResponseDto> call, @NonNull Response<ErrorResponseDto> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 204) {
                                view.logoutUser();
                            } else if (response.code() == 400) {
                                view.snackbarError(response.message());
                            }
                        }
                        view.progressBarDisabled();
                        view.snackbarError("body null");
                    }

                    @Override
                    public void onFailure(@NonNull Call<ErrorResponseDto> call, @NonNull Throwable t) {
                        view.progressBarDisabled();
                        view.snackbarError("onFailure");
                    }
                });
    }

    public interface View {
        void progressBarEnabled();
        void progressBarDisabled();
        void initAdapterForRecyclerView(List<MyMemInfo> myMemInfo);
        void setUsernameProfile(String username);
        void setDescriptionProfile(String description);
        void snackbarError(String error);
        void logoutUser();
    }
}
