package com.example.administrator.myapplication.module.video.list;

import android.graphics.pdf.PdfDocument;

import com.example.administrator.myapplication.api.RetrofitService;
import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.module.base.ILoadDataView;

import java.nio.file.Path;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoListPresenter implements IBasePresenter {

    private final ILoadDataView mView;
    private final String mVideoId;

    private int mPage=0;

    public VideoListPresenter(ILoadDataView mView, String mVideoId) {
        this.mView = mView;
        this.mVideoId = mVideoId;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getVideoList(mVideoId,mPage)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<List<VideoInfo>>bindToLife())
                .subscribe(new Observer<List<VideoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<VideoInfo> videoInfos) {
                        mView.loadData(videoInfos);
                        mPage++;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
