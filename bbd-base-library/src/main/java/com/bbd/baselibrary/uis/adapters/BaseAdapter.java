package com.bbd.baselibrary.uis.adapters;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

public abstract class BaseAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mItems;
    protected LayoutInflater mInflater;

    public BaseAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mItems = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(CommonHolder holder, T t, int position) {
                BaseAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(CommonHolder holder, T item, int position);

}
