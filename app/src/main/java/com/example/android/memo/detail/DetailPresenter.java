package com.example.android.memo.detail;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.android.memo.data.MemoContract;
import com.example.android.memo.data.MemoDbHelper;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;

    private MemoDbHelper mMemoDbHelper;

    @Override
    public void attachView(DetailContract.View view, Context context) {
        mView = view;
        mMemoDbHelper = new MemoDbHelper(context);

    }

    @Override
    public void detachView() {
        mView = null;
        mMemoDbHelper = null;
    }

    @Override
    public void loadMemo(long id) {
        Cursor cursor = mMemoDbHelper.getMemoCursorById(id);
        String memo = cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_CONTENTS));
        mView.updateMemo(memo);
    }

    @Override
    public void saveMemo(long id, String memo) {
        ContentValues cv = new ContentValues();
        cv.put(MemoContract.MemoEntry.COLUMN_CONTENTS, memo);
        if (mMemoDbHelper.updateMemo(id, cv)) {
            mView.moveToMainActivity();
        }
    }

    @Override
    public void deleteMemo(long id) {
        if (mMemoDbHelper.deleteMemo(id) > 0) {
            mView.moveToMainActivity();
        }
    }
}
