package com.bbd.baselibrary.uis.adapters.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bbd.baselibrary.R;
import com.bbd.baselibrary.uis.adapters.CommonHolder;


/**
 * created by Damon on 2017/7/4 9:20
 * <p>
 * description:
 */
public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;
    private boolean mIsLoading;

    public EmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    private boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {
            CommonHolder holder;
            if (mEmptyView != null) {
                holder = CommonHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = CommonHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
                setViewData(holder);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty()) {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty()) {
            WrapperUtils.setFullSpan(holder);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    private void setViewData(CommonHolder holder){
        if (mIsLoading) {
            holder.setText(R.id.tv_empty, "加载中...");
        } else {
            holder.setText(R.id.tv_empty, "暂无数据");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            setViewData((CommonHolder) holder);
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) return 1;
        return mInnerAdapter.getItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }


    public void setIsLoading(boolean mLoading) {
        mIsLoading = mLoading;
    }
}
