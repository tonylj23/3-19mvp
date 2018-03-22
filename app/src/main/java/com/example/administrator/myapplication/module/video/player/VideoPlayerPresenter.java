package com.example.administrator.myapplication.module.video.player;

import com.example.administrator.myapplication.AndroidApplication;
import com.example.administrator.myapplication.local.table.DanmakuInfo;
import com.example.administrator.myapplication.local.table.DanmakuInfoDao;
import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.local.table.VideoInfoDao;
import com.example.administrator.myapplication.utils.RxBus;

import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoPlayerPresenter implements IVideoPresenter{

    private final IVideoView mView;
    private final VideoInfoDao mDbDao;
    private final RxBus mRxBus;
    private final VideoInfo mVideoData;
    private final DanmakuInfoDao mDanmakuDao;
    private boolean mIsContains;

    public VideoPlayerPresenter(IVideoView mView, VideoInfo mVideoData) {
        this.mView = mView;
        this.mDbDao = AndroidApplication.getInstance().getDaoSession().getVideoInfoDao();
        this.mRxBus = AndroidApplication.getInstance().getmRxBus();
        this.mVideoData = mVideoData;
        this.mDanmakuDao = AndroidApplication.getInstance().getDaoSession().getDanmakuInfoDao();
        mIsContains = mDbDao.queryBuilder().list().contains(mVideoData);
    }

    @Override
    public void getData(boolean isRefresh) {
        List<VideoInfo> list = mDbDao.queryBuilder().list();
        io.reactivex.Observable.fromIterable(list)
                .filter(new Predicate<VideoInfo>() {
                    @Override
                    public boolean test(VideoInfo videoInfo) throws Exception {
                        return mVideoData.equals(videoInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<VideoInfo>bindToLife())
                .subscribe(new Consumer<VideoInfo>() {
                    @Override
                    public void accept(VideoInfo videoInfo) throws Exception {
                        mIsContains=true;
                        mView.loadData(videoInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void insert(Object data) {

    }

    @Override
    public void delete(Object data) {

    }

    @Override
    public void update(List list) {

    }

    @Override
    public void addDanmaku(DanmakuInfo danmakuInfo) {

    }

    @Override
    public void cleanDanmaku() {

    }
}
