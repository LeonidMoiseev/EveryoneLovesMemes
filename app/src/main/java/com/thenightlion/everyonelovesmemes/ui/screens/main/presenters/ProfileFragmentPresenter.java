package com.thenightlion.everyonelovesmemes.ui.screens.main.presenters;

import com.thenightlion.everyonelovesmemes.data.room.AppDatabase;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfo;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfoDao;
import com.thenightlion.everyonelovesmemes.utils.App;
import com.thenightlion.everyonelovesmemes.utils.SharedPreferencesUtils;

import java.util.List;

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

    public interface View {
        void progressBarEnabled();
        void progressBarDisabled();
        void initAdapterForRecyclerView(List<MyMemInfo> myMemInfo);
        void setUsernameProfile(String username);
        void setDescriptionProfile(String description);
    }
}
