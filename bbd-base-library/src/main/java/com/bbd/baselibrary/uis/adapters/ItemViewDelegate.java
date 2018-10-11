package com.bbd.baselibrary.uis.adapters;

/**
 *
 *created by Damon on 2017/5/27 14:53
 *
 *description: 
 *
 */

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(CommonHolder holder, T t, int position);
}
