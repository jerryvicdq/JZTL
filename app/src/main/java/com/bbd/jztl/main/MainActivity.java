package com.bbd.jztl.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbd.baselibrary.nets.callbacks.AbsAPICallback;
import com.bbd.baselibrary.nets.exceptions.ApiException;
import com.bbd.baselibrary.uis.activities.BaseActivity;
import com.bbd.jztl.R;
import com.bbd.jztl.account.LoginActivity;
import com.bbd.jztl.account.bean.LoginResponseBean;
import com.bbd.jztl.clue.activity.CreateClueActivity;
import com.bbd.jztl.net.AppNets;
import com.bbd.jztl.net.AppService;
import com.bbd.jztl.widget.SimplexToast;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.tv_main_message_dot)
    TextView tvMainMessageDot;
    @BindView(R.id.fl_main_message)
    FrameLayout flMainMessage;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.rl_photo)
    RelativeLayout rlPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.tv_police_number)
    TextView tvPoliceNumber;
    @BindView(R.id.tv_police_org)
    TextView tvPoliceOrg;
    @BindView(R.id.ll_main_cjxs)
    LinearLayout llMainCjxs;
    @BindView(R.id.tv_main_task_dot)
    TextView tvMainTaskDot;
    @BindView(R.id.rl_main_dclrw)
    RelativeLayout rlMainDclrw;
    @BindView(R.id.ll_main_wdccxs)
    LinearLayout llMainWdccxs;
    @BindView(R.id.ll_main_pqxzgs)
    LinearLayout llMainPqxzgs;
    @BindView(R.id.ll_main_wyclxs)
    LinearLayout llMainWyclxs;
    @BindView(R.id.ll_main_pqqtxs)
    LinearLayout llMainPqqtxs;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle saveInstanceState) {
        requestUserData();
    }


    @OnClick({R.id.fl_main_message, R.id.rl_photo, R.id.ll_main_cjxs, R.id.rl_main_dclrw, R.id.ll_main_wdccxs, R.id.ll_main_pqxzgs, R.id.ll_main_wyclxs, R.id.ll_main_pqqtxs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_main_message:
                break;
            case R.id.rl_photo:
                break;
            case R.id.ll_main_cjxs:
                startActivity(new Intent(this, CreateClueActivity.class));
                break;
            case R.id.rl_main_dclrw:
                break;
            case R.id.ll_main_wdccxs:
                break;
            case R.id.ll_main_pqxzgs:
                break;
            case R.id.ll_main_wyclxs:
                break;
            case R.id.ll_main_pqqtxs:
                break;
        }
    }

    private void requestUserData() {
        AppService.getInstance().getUser().subscribe(new AbsAPICallback<User>() {
            @Override
            protected void onResultError(ApiException ex) {
                dismissLoadingDialog();
                SimplexToast.show(MainActivity.this, ex.getDisplayMessage(), Gravity.CENTER);
            }

            @Override
            public void onNext(User user) {
                setUserData(user);
            }
        });
    }

    private void setUserData(User user) {
        tvName.setText(user.getName());
        tvRole.setText(user.getRole_name());
        tvPoliceNumber.setText("警号："+user.getPolice_code());
//        tvPoliceOrg.setText("单位："+user,get);
    }
}
