package com.example.administrator.myapplication.module.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.EmptyLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView,EmptyLayout.OnRetryListener{

    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout empty_layout;

    public boolean mSwipeRefresh;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayout());
        ButterKnife.bind(this);
        initView();
        initSwipeRefresh();
        updateView(false);
    }

    protected abstract void updateView(boolean isRefresh);

    @Override
    public void showLoading() {
        if(empty_layout!=null){
            empty_layout.setmEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void onRetry() {
        updateView(false);
    }

    @Override
    public void hideLoading() {
        if(empty_layout!=null){
            empty_layout.hide();
        }
    }

    @Override
    public void showNetError() {
        if(empty_layout!=null){
            empty_layout.setmEmptyStatus(EmptyLayout.STATUS_NO_NET);
            empty_layout.setOnRetryListener(this);
        }
    }

    private void initSwipeRefresh() {

    }

    protected abstract void initView();

    @LayoutRes
    protected abstract int attachLayout();

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    protected void addFragment(int conainerViewId, Fragment fragment,String tag){
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(conainerViewId,fragment,tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();

    }

    protected void replaceFragment(int containerViewId,Fragment fragment,String tag){
        if(getSupportFragmentManager().findFragmentByTag(tag)==null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerViewId,fragment,tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        }else{
            getSupportFragmentManager().popBackStack(tag,0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

