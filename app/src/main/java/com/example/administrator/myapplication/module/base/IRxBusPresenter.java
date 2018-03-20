package com.example.administrator.myapplication.module.base;


import io.reactivex.functions.Consumer;

/**
 * Created by long on 2016/9/2.
 * RxBus Presenter
 */
public interface IRxBusPresenter extends IBasePresenter {

    /**
     * 注册
     * @param eventType
     * @param <T>
     */
    <T> void registerRxBus(Class<T> eventType, Consumer<T> consumer);

    /**
     * 注销
     */
    void unregisterRxBus();
}
