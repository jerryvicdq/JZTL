package com.bbd.jztl.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bbd.baselibrary.uis.activities.BaseActivity;
import com.bbd.jztl.R;

/**
 * 类描述：
 * 创建人：xieyi
 * 创建时间：2017/7/11 11:19
 */

public abstract class BaseBlueStatusActivity extends BaseActivity {
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ViewGroup contentLayout = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        View statusBarView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
        statusBarView.setBackgroundColor(getResources().getColor(R.color.common_blue));
        contentLayout.addView(statusBarView, lp);
    }

    protected int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
