package com.bbd.baselibrary.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import com.bbd.baselibrary.R;
import com.bbd.baselibrary.utils.ScreenUtil;


@SuppressLint("AppCompatCustomView")
public class VerCodeButton extends Button {
    private final int TIME_OUT = 60; //60s
    private int curTimeOut = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            curTimeOut--;
            curTimeOut = Math.max(0, curTimeOut);
//            setText(getResources().getString(R.string.common_vercode_timedown, curTimeOut));
            if (curTimeOut != 0) {
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                resetTimeDown();
            }
        }
    };

    public VerCodeButton(Context context) {
        super(context);
    }

    public VerCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerCodeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = ScreenUtil.dp2px(30);
        int width = ScreenUtil.dp2px(90);
        setMeasuredDimension(width,height);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public synchronized void startTimeDown() {
        if (curTimeOut == 0) {
            this.setEnabled(false);
            curTimeOut = TIME_OUT;//重置时间
            handler.sendEmptyMessage(0);
        }
    }

    public synchronized void resetTimeDown() {
        curTimeOut = 0;
//        this.setText(R.string.common_btn_get_validate_code);
        this.setEnabled(true);
    }
}
