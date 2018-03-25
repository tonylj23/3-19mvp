package com.example.administrator.myapplication.module.photo.bigphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dl7.drag.DragSlopLayout;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.PhotoPagerAdapter;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.module.base.BaseActivity;
import com.example.administrator.myapplication.module.base.ILoadDataView;
import com.example.administrator.myapplication.module.base.ILocalPresenter;
import com.example.administrator.myapplication.module.photo.main.PhotoMainPresenter;
import com.example.administrator.myapplication.utils.DefIconFactory;
import com.example.administrator.myapplication.utils.ImageLoader;
import com.example.administrator.myapplication.utils.StringUtils;
import com.example.administrator.myapplication.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class BigPhotoActivity extends BaseActivity<ILocalPresenter> implements ILoadDataView<WelfarePhotoInfo>{
    private static final String BIG_PHOTO_KEY = "BigPhotoKey";
    private static final String PHOTO_INDEX_KEY = "PhotoIndexKey";
    private static final String FROM_LOVE_ACTIVITY = "FromLoveActivity";

    @BindView(R.id.photo)
    PhotoView mPhoto;
    @BindView(R.id.drag_layout)
    DragSlopLayout mDragLayout;

    private BigPhotoPresenter bigPhotoPresenter;
    private WelfarePhotoInfo parcelableExtra;

    public static void lunch(Context context, WelfarePhotoInfo data){
        Intent intent = new Intent(context,BigPhotoActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void loadData(WelfarePhotoInfo data) {
//        WindowManager wm = (WindowManager) this
//                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        int widthPixels = this.getResources().getDisplayMetrics().widthPixels;
//        int marginPixels = this.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
////        int mPhotoWidth = widthPixels / 2 - marginPixels;
////        int photoHeight = StringUtils.calcPhotoHeight(data.getPixel(), mPhotoWidth);
//        // 接口返回的数据有像素分辨率，根据这个来缩放图片大小
//        final ViewGroup.LayoutParams params = mPhoto.getLayoutParams();
//        params.width = width;
//        params.height = height;
//        mPhoto.setLayoutParams(params);
        ImageLoader.loadFitCenter(this, data.getUrl(), mPhoto, DefIconFactory.provideIcon());
    }

    @Override
    public void loadMoreData(WelfarePhotoInfo data) {

    }


    @Override
    public void loadNoData() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    protected void updateView(boolean isRefresh) {
        bigPhotoPresenter.getData(isRefresh);
    }

    @Override
    protected void initView() {
        parcelableExtra = (WelfarePhotoInfo)getIntent().getParcelableExtra("data");

//        mDragLayout.interactWithViewPager(false);
//        mDragLayout.setAnimatorMode(DragSlopLayout.FLIP_Y);
        bigPhotoPresenter = new BigPhotoPresenter(this, this, parcelableExtra);
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_big_photo;
    }
}
