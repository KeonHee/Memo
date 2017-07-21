package com.example.android.memo.detail;


import android.content.Context;

public interface DetailContract {

    interface View {
        void moveToMainActivity();

        void updateMemo(String memo);
    }

    interface Presenter {

        void attachView(DetailContract.View view, Context context);

        void detachView();

        void loadMemo(long id);

        void saveMemo(long id, String memo);

        void deleteMemo(long id);

    }
}
