package com.example.administrator.myapplication.module.news.newslist;

import com.example.administrator.myapplication.adapter.item.NewsMultiItem;
import com.example.administrator.myapplication.api.bean.NewsInfo;
import com.example.administrator.myapplication.module.base.ILoadDataView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public interface INewsListView extends ILoadDataView<List<NewsMultiItem>> {
    void loadAdData(NewsInfo newsInfo);
}
