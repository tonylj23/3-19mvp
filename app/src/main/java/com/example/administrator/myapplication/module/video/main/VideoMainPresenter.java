package com.example.administrator.myapplication.module.video.main;

import com.dl7.downloaderlib.model.DownloadStatus;
import com.example.administrator.myapplication.AndroidApplication;
import com.example.administrator.myapplication.local.table.VideoInfoDao;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;
import com.example.administrator.myapplication.utils.RxBus;
import com.orhanobut.logger.Logger;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoMainPresenter implements IRxBusPresenter{

    private final IVideoMainView mView;
    private final VideoInfoDao mDbDao;
    private final RxBus mRxBus;

    public VideoMainPresenter(IVideoMainView mView) {
        this.mView = mView;
        this.mDbDao = AndroidApplication.getInstance().getDaoSession().getVideoInfoDao();
        this.mRxBus = AndroidApplication.getInstance().getmRxBus();
    }

    @Override
    public void getData(boolean isRefresh) {
        mView.updateLovedCount((int) mDbDao.queryBuilder()
                .where(VideoInfoDao.Properties.IsCollect.eq(true)).count());
        mView.updateDownloadCount((int) mDbDao.queryBuilder()
        .where(VideoInfoDao.Properties.DownloadStatus.notIn(DownloadStatus.NORMAL, DownloadStatus.COMPLETE)).count());
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
