package com.example.administrator.myapplication.module.news.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ViewPagerAdapter;
import com.example.administrator.myapplication.local.table.NewsTypeInfo;
import com.example.administrator.myapplication.module.base.BaseFragment;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;
import com.example.administrator.myapplication.module.news.newslist.NewsListFragment;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;

/**
 * Created by long on 2016/12/20.
 * 新闻主界面
 */
public class NewsMainFragment extends BaseFragment<IRxBusPresenter> implements INewsMainView {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private NewsMainPresenter mainPresenter;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void initView() {
        initToolBar(mToolBar, true, "新闻");
        setHasOptionsMenu(true);
        mainPresenter = new NewsMainPresenter(this);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mainPresenter.getData(isRefresh);
    }

    @Override
    protected int attachLayout() {
        return R.layout.fragment_news_main;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.unregisterRxBus();
    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (NewsTypeInfo bean : checkList) {
            titles.add(bean.getName());
            fragments.add(NewsListFragment.newInstance(bean.getTypeId()));
        }
        viewPagerAdapter.setItems(fragments, titles);
    }
}
