package com.example.administrator.myapplication.module.news.article;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.bean.NewsDetailInfo;
import com.example.administrator.myapplication.module.base.BaseSwipeBackActivity;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.widget.PullScrollView;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;

/**
 * Created by lijunc on 2018/3/21.
 */

public class NewsArticleActivity extends BaseSwipeBackActivity<IBasePresenter> implements INewsArticleView{
    private static final String SHOW_POPUP_DETAIL = "ShowPopupDetail";
    private static final String NEWS_ID_KEY = "NewsIdKey";
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_pre_toolbar)
    LinearLayout mLlPreToolbar;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.scroll_view)
    PullScrollView mScrollView;
    private String mNewsId;
    private NewsArticlePresenter articlePresenter;


    public static void launch(Context context, String newsId){
        Intent intent = new Intent(context, NewsArticleActivity.class);
        intent.putExtra(NEWS_ID_KEY,newsId);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsId = getIntent().getStringExtra(NEWS_ID_KEY);
        articlePresenter = new NewsArticlePresenter(mNewsId, this);
        articlePresenter.getData(false);
    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void loadData(NewsDetailInfo detailInfo) {
        mTvTitle.setText(detailInfo.getTitle());
        mTvTime.setText(detailInfo.getPtime());
        RichText.from(detailInfo.getBody())
                .into(mTvContent);
    }

    @Override
    protected void updateView(boolean isRefresh) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_news_article;
    }
}
