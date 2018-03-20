package com.example.administrator.myapplication.module.base;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public interface ILoadDataView<T> extends IBaseView {
    void loadData(T data);

    void loadMoreData(T data);

    void loadNoData();
}
