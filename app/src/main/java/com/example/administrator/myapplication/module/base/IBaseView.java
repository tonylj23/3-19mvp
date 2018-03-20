package com.example.administrator.myapplication.module.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showNetError();

    void finishRefresh();
    <T> LifecycleTransformer<T> bindToLife();
}
