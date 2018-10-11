package com.bbd.baselibrary.uis.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


public class TabPagerAdapter<T extends ITabPager> extends FragmentStatePagerAdapter {
    private List<T> mList;
    private ITabContent tabContent;

    public TabPagerAdapter(FragmentManager fm, List<T> list, ITabContent tabContent) {
        super(fm);
        this.mList = list;
        this.tabContent = tabContent;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return tabContent.getContent(position);
    }
}
