package com.example.android.memo.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.memo.R;
import com.example.android.memo.data.MemoContract;

/**
 * Created by user on 2017-01-15.
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private EditText mMemoEdit;
    private Button mDeleteBtn;

    private DetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMemoEdit = (EditText) findViewById(R.id.et_memo);
        mDeleteBtn = (Button) findViewById(R.id.bt_delete_memo);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPresenter = new DetailPresenter();
        mPresenter.attachView(this, this);

        long id = getIntent().getLongExtra(MemoContract.MemoEntry._ID, 0);
        mPresenter.loadMemo(id);

        // delete button listener
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = getIntent().getLongExtra(MemoContract.MemoEntry._ID, 0);
                mPresenter.deleteMemo(id);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                long id = getIntent().getLongExtra(MemoContract.MemoEntry._ID, 0);
                String memoText = mMemoEdit.getText().toString();
                mPresenter.saveMemo(id, memoText);
                return true;
            case android.R.id.home:
                backToMainActivity();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void backToMainActivity() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void moveToMainActivity() {
        finish();
    }

    @Override
    public void updateMemo(final String memo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMemoEdit.setText(memo);
            }
        });
    }
}
