package com.bbd.baselibrary.uis.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bbd.baselibrary.utils.DialogHelper;
import com.example.swipebackactivity.app.SwipeBackActivity;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * created by Damon on 2017/5/27 11:35
 * <p>
 * description:
 */

public abstract class BaseActivity extends SwipeBackActivity {

    public ProgressDialog progressDialog;

    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        init(savedInstanceState);
        setSwipeBackEnable(false);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    protected abstract int getContentView();

    protected abstract void init(Bundle saveInstanceState);

//    protected void showLoadingMsg(String message) {
//        if (progressDialog != null) {
//            progressDialog.setMessage(message != null ? message : "处理中...");
//        } else {
//            progressDialog = new ProgressDialog(BaseActivity.this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setIndeterminate(false);
//            progressDialog.setCancelable(true);
//            progressDialog.setMessage(message != null ? message : "处理中...");
//        }
//        progressDialog.show();
//    }

//    protected void hideLoadingMsg() {
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//        }
//    }


    protected void showLoadingDialog(String message) {
        if (progressDialog == null) {
            progressDialog = DialogHelper.getProgressDialog(this, true);
        }
        progressDialog.setMessage(message != null ? message : "处理中...");
        progressDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

}
