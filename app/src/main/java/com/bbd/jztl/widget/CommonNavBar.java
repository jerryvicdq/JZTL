package com.bbd.jztl.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbd.jztl.R;


/**
 * 类描述：
 * 创建人： xieyi
 * 创建时间： 2017/7/10 18:37
 */

public class CommonNavBar extends RelativeLayout {

    View backView, rightBtn;
    TextView tvTitle, tvRight;
    Context mContext;
    ImageView ivRight;

    public CommonNavBar(Context context) {
        super(context);
        mContext = context;
    }

    public CommonNavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        backView = findViewById(R.id.nav_back);
        backView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).onBackPressed();
            }
        });
        tvTitle = (TextView) findViewById(R.id.nav_title);
        rightBtn = findViewById(R.id.right_btn);
        tvRight = (TextView) findViewById(R.id.tv_right_btn);
        ivRight = (ImageView) findViewById(R.id.img_right_btn);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void hideBack() {
        backView.setVisibility(View.INVISIBLE);
    }

    public void setRightBtn(String btnText, OnClickListener listener) {
        tvRight.setText(btnText);
        tvRight.setVisibility(View.VISIBLE);
        if (listener != null)
            rightBtn.setOnClickListener(listener);
    }

    public void setRightBtn(int imgSrc, OnClickListener listener) {
        ivRight.setImageResource(imgSrc);
        ivRight.setVisibility(View.VISIBLE);
        if (listener != null)
            rightBtn.setOnClickListener(listener);
    }


}
