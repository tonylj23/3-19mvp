package com.example.administrator.myapplication.module.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.EmptyLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends RxFragment implements IBaseView,EmptyLayout.OnRetryListener {
    @Nullable
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;
    protected Context mContext;

    private View mRootView;

    private boolean mIsMulti=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView=inflater.inflate(attachLayout(),null);
            ButterKnife.bind(this,mRootView);
            initView();
        }
        ViewGroup parent = (ViewGroup)mRootView.getParent();
        if(parent!=null){
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getUserVisibleHint()&&mRootView!=null&&!mIsMulti){
            mIsMulti=true;
            updateViews(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser&&isVisible()&&mRootView!=null&&!mIsMulti){
            mIsMulti=true;
            updateViews(false);
        }else{
            super.setUserVisibleHint(isVisibleToUser);
        }


    }

    protected abstract void initView();

    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setmEmptyStatus(EmptyLayout.STATUS_LOADING);
//            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity)getActivity()).initToolBar(toolbar,homeAsUpEnabled,title);
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
//            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
//            SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
        }
    }

    @Override
    public void showNetError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setmEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setOnRetryListener(this);
        }
    }


    protected abstract void updateViews(boolean isRefresh);

    @Override
    public void onRetry() {
        updateViews(false);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }


    protected abstract int attachLayout();
}
