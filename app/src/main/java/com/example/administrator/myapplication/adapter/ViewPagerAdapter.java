package com.example.administrator.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<String> mTitles;
    List<Fragment> fragments;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mTitles=new ArrayList<>();
        fragments=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    public void setItems(List<Fragment> fragments,List<String> titles){
        this.mTitles=titles;
        this.fragments=fragments;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
