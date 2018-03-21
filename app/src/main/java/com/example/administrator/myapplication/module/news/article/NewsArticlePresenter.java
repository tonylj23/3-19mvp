package com.example.administrator.myapplication.module.news.article;

import com.example.administrator.myapplication.api.RetrofitService;
import com.example.administrator.myapplication.api.bean.NewsDetailInfo;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.utils.ListUtils;
import com.example.administrator.myapplication.widget.EmptyLayout;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/3/21.
 */

public class NewsArticlePresenter implements IBasePresenter {

    private static final String HTML_IMG_TEMPLATE = "<img src=\"http\" />";


    private final String mNewsId;
    private final INewsArticleView mView;

    public NewsArticlePresenter(String mNewsId, INewsArticleView mView) {
        this.mNewsId = mNewsId;
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getNewsDetail(mNewsId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .doOnNext(new Consumer<NewsDetailInfo>() {
                    @Override
                    public void accept(NewsDetailInfo newsDetailInfo) throws Exception {
                        _handleRichTextWithImg(newsDetailInfo);
                    }
                }).compose(mView.<NewsDetailInfo>bindToLife())
                .subscribe(new Observer<NewsDetailInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsDetailInfo newsDetailInfo) {
                        mView.loadData(newsDetailInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
//                        mView.showNetError(new EmptyLayout.OnRetryListener() {
//                            @Override
//                            public void onRetry() {
//                                getData(false);
//                            }
//                        });
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });

    }

    private void _handleRichTextWithImg(NewsDetailInfo newsDetailInfo){
        if(!ListUtils.isEmpty(newsDetailInfo.getImg())){
            String body = newsDetailInfo.getBody();
            for(NewsDetailInfo.ImgEntity imgEntity:newsDetailInfo.getImg()){
                String ref = imgEntity.getRef();
                String src = imgEntity.getSrc();
                String img = HTML_IMG_TEMPLATE.replace("http", src);
                body = body.replaceAll(ref, img);
                Logger.w(img);
                Logger.i(body);
            }
            newsDetailInfo.setBody(body);
        }
    }

    @Override
    public void getMoreData() {

    }
}
