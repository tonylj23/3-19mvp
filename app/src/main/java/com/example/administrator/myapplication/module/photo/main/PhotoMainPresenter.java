package com.example.administrator.myapplication.module.photo.main;

import com.example.administrator.myapplication.AndroidApplication;
import com.example.administrator.myapplication.local.table.BeautyPhotoInfo;
import com.example.administrator.myapplication.local.table.BeautyPhotoInfoDao;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;
import com.example.administrator.myapplication.utils.RxBus;
import com.orhanobut.logger.Logger;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/3/22.
 */

public class PhotoMainPresenter implements IRxBusPresenter {

    private final IPhotoMainView mView;
    private final BeautyPhotoInfoDao mDbDao;
    private final RxBus mRxBus;

    public PhotoMainPresenter(IPhotoMainView mView) {
        this.mView = mView;
        this.mDbDao = AndroidApplication.getInstance().getDaoSession().getBeautyPhotoInfoDao();
        this.mRxBus = AndroidApplication.getInstance().getmRxBus();
    }

    @Override
    public void getData(boolean isRefresh) {
        mView.updateCount((int)mDbDao.queryBuilder().where(BeautyPhotoInfoDao.Properties.IsLove.eq(true)).count());
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Consumer<T> consumer) {
        Disposable disposable = mRxBus.doSubscribe(eventType, consumer, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.e(throwable.toString());
            }
        });
        mRxBus.addSubscription(this,disposable);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
