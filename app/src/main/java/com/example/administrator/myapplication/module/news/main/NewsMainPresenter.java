package com.example.administrator.myapplication.module.news.main;

import com.example.administrator.myapplication.AndroidApplication;
import com.example.administrator.myapplication.local.table.DaoSession;
import com.example.administrator.myapplication.local.table.NewsTypeInfo;
import com.example.administrator.myapplication.local.table.NewsTypeInfoDao;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;
import com.example.administrator.myapplication.utils.RxBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class NewsMainPresenter implements IRxBusPresenter {

    private final INewsMainView mView;
    private final NewsTypeInfoDao mDao;
    private final RxBus mRxBus;

    public NewsMainPresenter(INewsMainView mView) {
        this.mView = mView;
        this.mDao = AndroidApplication.getInstance().getDaoSession().getNewsTypeInfoDao();
        this.mRxBus = AndroidApplication.getInstance().getmRxBus();
    }

    @Override
    public void getData(boolean isRefresh) {
        Observable.just(mDao.queryBuilder().list())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsTypeInfo>>() {
                    @Override
                    public void accept(List<NewsTypeInfo> newsTypeInfos) throws Exception {
                        mView.loadData(newsTypeInfos);
                    }
                });

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
