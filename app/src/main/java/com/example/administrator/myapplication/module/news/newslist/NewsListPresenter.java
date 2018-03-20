package com.example.administrator.myapplication.module.news.newslist;

import android.widget.Toast;

import com.example.administrator.myapplication.adapter.item.NewsMultiItem;
import com.example.administrator.myapplication.api.NewsUtils;
import com.example.administrator.myapplication.api.RetrofitService;
import com.example.administrator.myapplication.api.bean.NewsInfo;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.BiConsumerSingleObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.ResourceSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class NewsListPresenter implements IBasePresenter {

    private INewsListView mView;
    private String mNewsId;

    private int mPage = 0;

    public NewsListPresenter(INewsListView mView, String mNewsId) {
        this.mView = mView;
        this.mNewsId = mNewsId;
    }

    @Override
    public void getData(final boolean isRefresh) {
        Observable<List<NewsInfo>> newsList = RetrofitService.getNewsList(mNewsId, mPage);
        newsList .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<NewsInfo>, ObservableSource<NewsInfo>>() {
                    @Override
                    public ObservableSource<NewsInfo> apply(List<NewsInfo> newsInfos) throws Exception {
                        return Observable.fromIterable(newsInfos);
                    }
                })
                .filter(new Predicate<NewsInfo>() {
                    @Override
                    public boolean test(NewsInfo newsInfo) throws Exception {
                        if (NewsUtils.isAbNews(newsInfo)) {
                            mView.loadAdData(newsInfo);
                        }
                        return !NewsUtils.isAbNews(newsInfo);
                    }
                })
                .map(new Function<NewsInfo, NewsMultiItem>() {
                    @Override
                    public NewsMultiItem apply(NewsInfo newsInfo) throws Exception {
                        return new NewsMultiItem(newsInfo);
                    }
                }).toList()
                .compose(mView.<List<NewsMultiItem>>bindToLife())
                .subscribe(new SingleObserver<List<NewsMultiItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<NewsMultiItem> newsMultiItems) {
                        mView.loadData(newsMultiItems);
                        mPage++;
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            mView.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
//                    ToastUtils.showToast("刷新失败提示什么根据实际情况");
                        } else {
//                    Toast.makeText()
//                    ToastUtils.showToast("网络异常");
                        }
                    }
                });


    }

//    private ObservableTransformer<NewsInfo, List<NewsMultiItem>> mTransformer = new ObservableTransformer<NewsInfo, List<NewsMultiItem>>() {
//        @Override
//        public ObservableSource<List<NewsMultiItem>> apply(Observable<NewsInfo> upstream) {
//            return upstream.map(new Function<NewsInfo, NewsMultiItem>() {
//                @Override
//                public NewsMultiItem apply(NewsInfo newsInfo) throws Exception {
//                    if (NewsUtils.isNewsPhotoSet(newsInfo.getSkipType())) {
//                        return new NewsMultiItem(newsInfo);
//                    }
//                    return new NewsMultiItem(newsInfo);
//                }
//            }).;
//        }
//    };

    @Override
    public void getMoreData() {

    }
}
