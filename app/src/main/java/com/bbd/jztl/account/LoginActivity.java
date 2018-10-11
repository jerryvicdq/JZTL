package com.bbd.jztl.account;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bbd.baselibrary.nets.callbacks.AbsAPICallback;
import com.bbd.baselibrary.nets.exceptions.ApiException;
import com.bbd.baselibrary.utils.DialogHelper;
import com.bbd.baselibrary.utils.NetworkManager;
import com.bbd.jztl.R;
import com.bbd.jztl.account.bean.LoginResponseBean;
import com.bbd.jztl.base.BaseBlueStatusActivity;
import com.bbd.jztl.main.MainActivity;
import com.bbd.jztl.net.AppNets;
import com.bbd.jztl.net.AppService;
import com.bbd.jztl.widget.SimplexToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述：登录页面
 * 创建人：圈圈D
 * 创建时间：2018-10-08 16:03
 */
public class LoginActivity extends BaseBlueStatusActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    @BindView(R.id.iv_login_logo)
    ImageView ivLoginLogo;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView ivLoginUsernameDel;
    @BindView(R.id.ll_login_username)
    LinearLayout llLoginUsername;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView ivLoginPwdDel;
    @BindView(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.lay_login_container)
    LinearLayout layLoginContainer;

    protected InputMethodManager mInputMethodManager;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle saveInstanceState) {
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        initLisenter();
    }

    private void initLisenter() {
        etLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString().trim();
                if (username.length() > 0) {
                    ivLoginUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    ivLoginUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = etLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }

            }
        });

        etLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    ivLoginPwdDel.setVisibility(View.VISIBLE);
                } else {
                    ivLoginPwdDel.setVisibility(View.INVISIBLE);
                }

                String username = etLoginUsername.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });
    }


    @OnClick({R.id.iv_login_username_del, R.id.iv_login_pwd_del, R.id.btn_login, R.id.lay_login_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_username_del:
                etLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                etLoginPwd.setText(null);
                break;
            case R.id.btn_login:
                if (checkLogin()) {
                    login();
                }
                break;
            case R.id.lay_login_container:
                try {
                    hideKeyBoard(getCurrentFocus().getWindowToken());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void login() {
        showLoadingDialog("登录中...");
        AppService.getInstance().login(etLoginUsername.getText().toString(), etLoginPwd.getText().toString()).subscribe(new AbsAPICallback<LoginResponseBean>() {
            @Override
            protected void onResultError(ApiException ex) {
                dismissLoadingDialog();
                SimplexToast.show(LoginActivity.this, ex.getDisplayMessage(), Gravity.CENTER);
            }

            @Override
            public void onNext(LoginResponseBean result) {
                AppNets.getInstance().setToken("Bearer " + result.getAccess_token());
                dismissLoadingDialog();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean checkLogin() {
        String username = etLoginUsername.getText().toString().trim();
        String pwd = etLoginPwd.getText().toString().trim();
        if (username.length() < 6 || pwd.length() < 6) {
            DialogHelper.getMessageDialog(this, "温馨提示", "账号或密码格式有误，请重新输入", "确定").show();
            return false;
        }
        if (!NetworkManager.getInstance(this).isNetworkConnected()) {
            DialogHelper.getMessageDialog(this, "温馨提示", "网络异常，请稍后重新输入", "确定").show();
            return false;
        }
        return true;
    }

    private int mLogoHeight;
    private int mLogoWidth;

    @Override
    public void onGlobalLayout() {
        final ImageView ivLogo = this.ivLoginLogo;
        Rect KeypadRect = new Rect();

        ivLogo.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = ivLogo.getRootView().getHeight();

        int keypadHeight = screenHeight - KeypadRect.bottom;

//        if (keypadHeight > 0) {
//            updateKeyBoardActiveStatus(true);
//        } else {
//            updateKeyBoardActiveStatus(false);
//        }
        if (keypadHeight > 0 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;
            ivLogo.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();


        } else if (keypadHeight == 0 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;
            ivLogo.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivLoginLogo.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            hideKeyBoard(getCurrentFocus().getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ivLoginLogo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    protected void hideKeyBoard(IBinder windowToken) {
        if (windowToken == null) {
            return;
        }
        InputMethodManager inputMethodManager = this.mInputMethodManager;
        if (inputMethodManager == null) return;
        boolean active = inputMethodManager.isActive();
        if (active) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
