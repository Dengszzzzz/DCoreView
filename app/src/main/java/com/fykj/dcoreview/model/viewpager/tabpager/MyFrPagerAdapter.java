package com.fykj.dcoreview.model.viewpager.tabpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengzh on 2018/4/23.
 */

public class MyFrPagerAdapter<T extends Fragment> extends FragmentPagerAdapter{

    private List<T> mFrList;

    public MyFrPagerAdapter(FragmentManager fm,ArrayList<T> fragments) {
        super(fm);
        mFrList = fragments;
    }

    @Override
    public T getItem(int position) {
        T fragment = mFrList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFrList  == null ? 0:mFrList.size();
    }
}
