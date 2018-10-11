package com.bbd.baselibrary.mvp;

/**
 * Created by xy on 2017/6/1.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoading(String message);

    void hideLoading();

}
