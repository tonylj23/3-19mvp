package com.example.administrator.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class EmptyLayout extends FrameLayout {
    private Context mContext;
    private int bgColor;
    public static final int STATUS_HIDE=1001;
    public static final int STATUS_LOADING=1;
    public static final int STATUS_NO_NET=2;
    public static final int STATUS_NO_DATA=3;

    private int mEmptyStatus=STATUS_LOADING;
    private OnRetryListener onRetryListener;

    @BindView(R.id.empty_layout)
    FrameLayout empty_layout;
    @BindView(R.id.empty_loading)
    SpinKitView empty_loading;
    @BindView(R.id.net_error_tv)
    TextView net_error_tv;

    public interface OnRetryListener{
        void onRetry();
    }

    public void hide(){
        mEmptyStatus=STATUS_HIDE;
        _switchEmptyView();
    }
    public int getmEmptyStatus() {
        return mEmptyStatus;
    }

    public void setmEmptyStatus(int mEmptyStatus) {
        this.mEmptyStatus = mEmptyStatus;
        _switchEmptyView();
    }

    public void setOnRetryListener(OnRetryListener onRetryListener){
        this.onRetryListener=onRetryListener;
    }
    public EmptyLayout(@NonNull Context context) {
        this(context,null);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
    }
    private void init(AttributeSet attributeSet){
        TypedArray array = null;
        try{
            array = mContext.obtainStyledAttributes(attributeSet, R.styleable.EmptyLayout);
            bgColor = array.getColor(R.styleable.EmptyLayout_empty_background, Color.WHITE);
        }finally {
            array.recycle();
        }
        ButterKnife.bind(this);
        View inflate = View.inflate(mContext, R.layout.layout_empty_loading, this);
        empty_layout.setBackgroundColor(bgColor);
        _switchEmptyView();

    }




    private void _switchEmptyView() {
        switch (mEmptyStatus){
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                net_error_tv.setVisibility(GONE);
                empty_loading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                net_error_tv.setVisibility(VISIBLE);
                empty_loading.setVisibility(GONE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }

    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING,STATUS_NO_NET,STATUS_NO_DATA})
    public @interface EmptyStatus{}
}
