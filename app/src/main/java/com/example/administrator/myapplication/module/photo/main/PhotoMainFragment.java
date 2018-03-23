package com.example.administrator.myapplication.module.photo.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ViewPagerAdapter;
import com.example.administrator.myapplication.module.base.BaseFragment;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;
import com.example.administrator.myapplication.module.news.newslist.NewsListFragment;
import com.example.administrator.myapplication.module.photo.welfare.WelfareListFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by lijunc on 2018/3/22.
 */

public class PhotoMainFragment extends BaseFragment<IRxBusPresenter> implements IPhotoMainView{

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private PhotoMainPresenter presenter;
    private ViewPagerAdapter pagerAdapter;

    //    @BindView(R.id.iv_count)
//    TextView mIvCount;
    @Override
    public void updateCount(int lovedCount) {

    }

    @Override
    protected void initView() {
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        initToolBar(mToolBar,true,"图片");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new WelfareListFragment());
        pagerAdapter.setItems(fragments, Arrays.asList(new String[]{"福利"}));
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        presenter = new PhotoMainPresenter(this);

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected int attachLayout() {
        return R.layout.fragment_photo_main;
    }
}
