package com.thenightlion.everyonelovesmemes.ui.screens.main.presenters;

import android.text.TextUtils;

import com.thenightlion.everyonelovesmemes.data.model.MemDto;
import com.thenightlion.everyonelovesmemes.data.room.App;
import com.thenightlion.everyonelovesmemes.data.room.AppDatabase;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfo;
import com.thenightlion.everyonelovesmemes.data.room.MyMemInfoDao;

public class AddMemesFragmentPresenter {

    private MemDto memDto;
    private View view;

    public AddMemesFragmentPresenter(View view) {
        this.memDto = new MemDto();
        this.view = view;
    }

    public void updateTitleMem(String title) {
        memDto.setTitle(title);
    }

    public void updateDescriptionMem(String description) {
        memDto.setDescription(description);
    }

    public void updatePhotoUtl(String url) {
        memDto.setPhotoUtl(url);
    }

    public void addMemInDatabase() {
        AppDatabase db = App.getInstance().getDatabase();
        MyMemInfoDao myMemInfoDao = db.myMemInfoDao();
        MyMemInfo myMemInfo = new MyMemInfo();
        myMemInfo.setTitle(memDto.getTitle());
        myMemInfo.setDescription(memDto.getDescription());
        myMemInfo.setPhotoUtl(memDto.getPhotoUtl());
        myMemInfo.setCreatedDate(System.currentTimeMillis());
        myMemInfoDao.insert(myMemInfo);
    }

    public void checkEnabledButtonCreateMem(String title, boolean imageLoad) {
        if (!TextUtils.isEmpty(title) && imageLoad) {
            view.enabledButtonCreateMem();
        } else {
            view.disabledButtonCreateMem();
        }
    }

    public interface View {
        void enabledButtonCreateMem();
        void disabledButtonCreateMem();
    }
}
