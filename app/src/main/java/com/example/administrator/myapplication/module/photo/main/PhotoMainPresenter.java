package com.example.administrator.myapplication.module.photo.main;

import com.example.administrator.myapplication.module.base.IRxBusPresenter;

import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/3/22.
 */

public class PhotoMainPresenter implements IRxBusPresenter {

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Consumer<T> consumer) {

    }

    @Override
    public void unregisterRxBus() {

    }
}
