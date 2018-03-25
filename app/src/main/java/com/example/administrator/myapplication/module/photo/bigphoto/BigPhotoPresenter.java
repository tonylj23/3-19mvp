package com.example.administrator.myapplication.module.photo.bigphoto;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.ViewGroup;

import com.example.administrator.myapplication.AndroidApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.module.base.ILoadDataView;
import com.example.administrator.myapplication.module.base.ILocalPresenter;
import com.example.administrator.myapplication.utils.DefIconFactory;
import com.example.administrator.myapplication.utils.ImageLoader;
import com.example.administrator.myapplication.utils.RxBus;
import com.example.administrator.myapplication.utils.StringUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class BigPhotoPresenter implements ILocalPresenter<WelfarePhotoInfo>{

    private final ILoadDataView mView;
    private final WelfarePhotoInfo mWelfareList;
    private final RxBus mRxBus;
    private Context mContext;

    private int mPage=1;

    public BigPhotoPresenter(Context context, ILoadDataView mView, WelfarePhotoInfo mWelfareList) {
        this.mView = mView;
        this.mWelfareList = mWelfareList;
        this.mRxBus = AndroidApplication.getInstance().getmRxBus();
        this.mContext=context;
    }

    @Override
    public void getData(boolean isRefresh) {
        Observable.just(mWelfareList)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                }).subscribe(new Consumer<WelfarePhotoInfo>() {
            @Override
            public void accept(WelfarePhotoInfo welfarePhotoInfo) throws Exception {
                mView.loadData(welfarePhotoInfo);

            }
        });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void insert(WelfarePhotoInfo data) {

    }

    @Override
    public void delete(WelfarePhotoInfo data) {

    }

    @Override
    public void update(List<WelfarePhotoInfo> list) {

    }
}
