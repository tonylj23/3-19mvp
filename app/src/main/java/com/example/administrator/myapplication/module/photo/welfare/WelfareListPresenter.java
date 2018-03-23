package com.example.administrator.myapplication.module.photo.welfare;

import com.example.administrator.myapplication.api.RetrofitService;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.utils.ImageLoader;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lijunc on 2018/3/23.
 */

public class WelfareListPresenter implements IBasePresenter {

    private WelfareListFragment mView;
    private int mPage=1;

    public WelfareListPresenter(WelfareListFragment mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getWelfarePhoto(mPage)
                .observeOn(Schedulers.io())
                .filter(new Predicate<WelfarePhotoInfo>() {
                    @Override
                    public boolean test(WelfarePhotoInfo welfarePhotoInfo) throws Exception {
                        try{
                            welfarePhotoInfo.setPixel(ImageLoader.calePhotoSize(mView.getContext(),welfarePhotoInfo.getUrl()));
                            return true;
                        }catch (Exception e){
                            return false;
                        }

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .toList()
                .compose(mView.<List<WelfarePhotoInfo>>bindToLife())
                .subscribe(new SingleObserver<List<WelfarePhotoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<WelfarePhotoInfo> welfarePhotoInfos) {
                        mView.loadData(welfarePhotoInfos);
                        mPage++;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
