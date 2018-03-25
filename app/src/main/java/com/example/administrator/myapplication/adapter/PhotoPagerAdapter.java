package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.print.PageRange;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.utils.ImageLoader;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Collections;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private final static int LOAD_MORE_LIMIT=3;
    private List<WelfarePhotoInfo> mImgList;
    private Context mContext;

    public PhotoPagerAdapter(List<WelfarePhotoInfo> mImgList, Context mContext) {
        this.mImgList = mImgList;
        this.mContext = mContext;
    }

    public PhotoPagerAdapter(Context mContext) {
        this.mContext = mContext;
        this.mImgList= Collections.EMPTY_LIST;
    }

    @Override
    public int getCount() {
        return mImgList.size();
    }

    public void updateData(List<WelfarePhotoInfo> imgList){
        this.mImgList=imgList;
        notifyDataSetChanged();
    }

    public void addData(List<WelfarePhotoInfo> imgList) {
        mImgList.addAll(imgList);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_pager, null, false);
        final PhotoView photo = (PhotoView) view.findViewById(R.id.iv_photo);
        final SpinKitView loadingView = (SpinKitView) view.findViewById(R.id.loading_view);
        final TextView tvReload = (TextView) view.findViewById(R.id.tv_reload);
        RequestListener<String, GlideDrawable> requestListener=new RequestListener<String, GlideDrawable>(){
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                photo.setImageDrawable(resource);
                return true;
            }
        };
        ImageLoader.loadCenterCrop(mContext, mImgList.get(position % mImgList.size()).getUrl(), photo, requestListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public WelfarePhotoInfo getData(int position){
        return mImgList.get(position);
    }

    public WelfarePhotoInfo getData(String url) {
        for (WelfarePhotoInfo bean : mImgList) {
            if (bean.getUrl().equals(url)) {
                return bean;
            }
        }
        return null;
    }
}
