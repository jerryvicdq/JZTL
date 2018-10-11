package com.bbd.baselibrary.uis.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bbd.baselibrary.R;
import com.bbd.baselibrary.uis.adapters.MultiItemTypeAdapter;
import com.bbd.baselibrary.uis.adapters.wrapper.EmptyWrapper;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

/**
 * created by Damon on 2017/6/19 10:13
 * <p>
 * description:
 */

public abstract class BaseRefreshLoadingActivity<T> extends BaseActivity implements SwipeRefreshHelper.OnSwipeRefreshListener,
        OnLoadMoreListener, RecyclerAdapterWithHF.OnItemClickListener {

    protected int FIRST_PAGE = 1;
    protected SwipeRefreshHelper mSwipeRefreshHelper;
    protected SwipeRefreshLayout mLayoutRefresh;
    protected RecyclerView mRecyclerView;

    protected ArrayList<T> mItems = new ArrayList<>();
    protected MultiItemTypeAdapter<T> mInnerAdapter;
    protected EmptyWrapper<T> mEmptyWrapper;
    protected RecyclerAdapterWithHF mAdapter;

    protected RecyclerView.LayoutManager mLayoutManager;
    protected int mCurrPage = FIRST_PAGE;
    protected boolean mIsAutoRefresh = true;

    protected void setupRecyclerView() {
        mRecyclerView.setLayoutManager(initLayoutManager());
        mRecyclerView.setAdapter(initAdapter());
        mAdapter.setOnItemClickListener(this);
        if (isShowDivider()) setDivider();
    }

    protected void setDivider() {
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.black_divider))
                .sizeResId(R.dimen.spacing_divider)
                .build());
    }

    protected boolean isShowDivider() {
        return true;
    }

    protected RecyclerView.LayoutManager initLayoutManager() {
        mLayoutManager = getLayoutManager();
        return mLayoutManager;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected RecyclerAdapterWithHF initAdapter() {
        mInnerAdapter = getAdapter();
        mEmptyWrapper = new EmptyWrapper<>(mInnerAdapter);
        mEmptyWrapper.setEmptyView(getEmptyViewId());
        mAdapter = new RecyclerAdapterWithHF(mEmptyWrapper);
        mEmptyWrapper.setIsLoading(true);
        return mAdapter;
    }

    protected int getEmptyViewId() {
        return R.layout.ui_layout_empty;
    }

    protected void setLoadingView() {
        mEmptyWrapper.setEmptyView(R.layout.ui_layout_loading);
    }

    private void setupRefreshAndLoadMore() {
        mSwipeRefreshHelper = new SwipeRefreshHelper(mLayoutRefresh);
        mSwipeRefreshHelper.setOnSwipeRefreshListener(this);
        mSwipeRefreshHelper.setOnLoadMoreListener(this);

        if (isAutoRefresh()) {
            autoRefresh();
        }

    }

    protected void autoRefresh() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshHelper.autoRefresh();
            }
        }, 100);
    }

    @Override
    public void onfresh() {
        mCurrPage = FIRST_PAGE;
        loadData(mCurrPage);
    }

    @Override
    public void loadMore() {
        loadData(++mCurrPage);
    }

    protected void refreshComplete(boolean loadSuccess) {
        if (!loadSuccess && mCurrPage > FIRST_PAGE) {
            mCurrPage--;
        }
        mEmptyWrapper.setIsLoading(false);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshHelper.refreshComplete();
        mSwipeRefreshHelper.setLoadMoreEnable(loadSuccess && mItems.size() >= 10 && (mItems.size() % 10 == 0));
        if (mCurrPage > FIRST_PAGE) {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }
    }

    @Override
    public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
//        LogUtil.d(position);
    }

    protected abstract MultiItemTypeAdapter<T> getAdapter();

    protected abstract void loadData(int page);

//    public boolean isAutoRefresh() {
//        return true;
//    }

    public boolean isAutoRefresh() {
        return mIsAutoRefresh;
    }

    @Override
    protected void init(Bundle saveInstanceState) {
        mLayoutRefresh = (SwipeRefreshLayout) findViewById(R.id.pre_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.pre_recycler_view);
        mLayoutRefresh.setColorSchemeResources(R.color.colorAccent);
        setupRecyclerView();
        setupRefreshAndLoadMore();
    }
}