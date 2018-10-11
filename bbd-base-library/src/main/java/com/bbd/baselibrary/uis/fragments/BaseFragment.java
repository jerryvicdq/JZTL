package com.bbd.baselibrary.uis.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbd.baselibrary.uis.activities.BaseActivity;

import butterknife.ButterKnife;

/**
 *
 *created by Damon on 2017/6/2 10:04
 *
 *description: 
 *
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;//缓存Fragment view

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView==null){
            rootView=inflater.inflate(getFragmentView(),container,false);
            ButterKnife.bind(this,rootView);
            init(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public abstract @LayoutRes
    int getFragmentView();

    public abstract void init(View rootView);

    public void hideView(View view){
        if(view!=null){
            view.setVisibility(View.GONE);
        }
    }

    public void showView(View view){
        if(view!=null){
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void showLoadingMsg(String message){
        if (progressDialog != null) {
            progressDialog.setMessage(message != null ? message : "处理中...");
        } else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage(message != null ? message : "处理中...");
        }
        progressDialog.show();
    }

    protected void hideLoadingMsg(){
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
