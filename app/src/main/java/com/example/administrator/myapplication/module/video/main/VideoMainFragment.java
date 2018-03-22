package com.example.administrator.myapplication.module.video.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ViewPagerAdapter;
import com.example.administrator.myapplication.module.base.BaseFragment;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;
import com.example.administrator.myapplication.module.video.list.VideoListFragment;
import com.example.administrator.myapplication.rxbus.event.VideoEvent;
import com.flyco.tablayout.SlidingTabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoMainFragment extends BaseFragment<IRxBusPresenter> implements IVideoMainView{

    private final String[] VIDEO_ID = new String[]{
            "V9LG4B3A0", "V9LG4E6VR", "V9LG4CHOR", "00850FRB"
    };
    private final String[] VIDEO_TITLE = new String[]{
            "热点", "搞笑", "娱乐", "精品"
    };

    @BindView(R.id.iv_love_count)
    TextView mIvLoveCount;
    @BindView(R.id.fl_love_layout)
    FrameLayout mFlLoveLayout;
    @BindView(R.id.tv_download_count)
    TextView mTvDownloadCount;
    @BindView(R.id.fl_download_layout)
    FrameLayout mFlDownloadLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private VideoMainPresenter videoMainPresenter;
    private ViewPagerAdapter pagerAdapter;

    @Override
    public void updateLovedCount(int lovedCount) {

    }

    @Override
    public void updateDownloadCount(int downloadCount) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoMainPresenter = new VideoMainPresenter(this);
    }



    @Override
    protected void initView() {
        initToolBar(mToolBar,true,"视频");
        videoMainPresenter.registerRxBus(VideoEvent.class, new Consumer<VideoEvent>() {
            @Override
            public void accept(VideoEvent videoEvent) throws Exception {
                if(videoEvent.checkStatus==VideoEvent.CHECK_INVALID){
                    videoMainPresenter.getData(false);
                }
            }
        });
        pagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<VIDEO_ID.length;i++){
            fragments.add(VideoListFragment.newInstance(VIDEO_ID[i]));
        }
        pagerAdapter.setItems(fragments, Arrays.asList(VIDEO_TITLE));
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        videoMainPresenter.getData(isRefresh);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoMainPresenter.unregisterRxBus();
    }

    @Override
    protected int attachLayout() {
        return R.layout.fragment_video_main;
    }
}
