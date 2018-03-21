package com.example.administrator.myapplication.module.news.article;

import com.example.administrator.myapplication.api.bean.NewsDetailInfo;
import com.example.administrator.myapplication.module.base.IBaseView;

/**
 * Created by lijunc on 2018/3/21.
 */

public interface INewsArticleView extends IBaseView {
    void loadData(NewsDetailInfo detailInfo);
}
