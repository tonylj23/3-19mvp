package com.example.administrator.myapplication.module.news.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.NewsListAdapter;
import com.example.administrator.myapplication.adapter.item.NewsMultiItem;
import com.example.administrator.myapplication.api.bean.NewsInfo;
import com.example.administrator.myapplication.module.base.BaseFragment;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.utils.RecyclerViewHelper;
import com.example.administrator.myapplication.utils.SliderHelper;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class NewsListFragment extends BaseFragment<IBasePresenter> implements INewsListView {

    private static final String NEWS_TYPE_KEY = "NewsTypeKey";
    @Nullable
    @BindView(R.id.rv_news_list)
    RecyclerView mRvNewsList;

    SliderLayout mAdSlider;
    private String mNewsId;
    private NewsListPresenter newsListPresenter;
    private NewsListAdapter newsListAdapter;


    public static NewsListFragment newInstance(String newsId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_TYPE_KEY, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mNewsId=getArguments().getString(NEWS_TYPE_KEY);
        }
        newsListPresenter = new NewsListPresenter(this, mNewsId);


    }

    @Override
    public void loadData(List<NewsMultiItem> data) {
        newsListAdapter.setNewData(data);

//        if (isDivided) {
//            view.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//        }
    }

    @Override
    public void loadMoreData(List<NewsMultiItem> data) {
        newsListAdapter.loadMoreComplete();
        newsListAdapter.addData(data);
    }

    @Override
    public void loadNoData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdSlider != null) {
            mAdSlider.startAutoCycle();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdSlider != null) {
            mAdSlider.stopAutoCycle();
        }
    }

    @Override
    public void loadAdData(NewsInfo newsInfo) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.head_news_list, null);
        mAdSlider = view.findViewById(R.id.slider_ads);
        SliderHelper.initAdSlider(mContext, mAdSlider, newsInfo);
        int headerLayoutCount = newsListAdapter.getHeaderLayoutCount();

//        newsListAdapter.addHeaderView(view);
        Logger.d("headerLayoutCount::"+headerLayoutCount);
    }

    @Override
    protected void initView() {
        newsListAdapter = new NewsListAdapter(null);

        mRvNewsList.setAdapter(newsListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        view.setHasFixedSize(true);
        mRvNewsList.setLayoutManager(layoutManager);
        mRvNewsList.setItemAnimator(new DefaultItemAnimator());
//        SlideInRightAnimationAdapter animAdapter = new SlideInRightAnimationAdapter(newsListAdapter);
//        RecyclerViewHelper.initRecyclerViewV(mContext, mRvNewsList, true, new AlphaInAnimationAdapter(animAdapter));
        newsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                newsListPresenter.getMoreData();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        newsListPresenter.getData(isRefresh);
    }

    @Override
    protected int attachLayout() {
        return R.layout.fragment_news_list;
    }
}
