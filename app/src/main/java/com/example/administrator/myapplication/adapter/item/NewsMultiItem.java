package com.example.administrator.myapplication.adapter.item;

import android.support.annotation.IntDef;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.administrator.myapplication.api.NewsUtils;
import com.example.administrator.myapplication.api.bean.NewsInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by long on 2016/8/24.
 * 新闻复用列表项
 */
public class NewsMultiItem implements MultiItemEntity {

    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_PHOTO_SET = 2;

    private NewsInfo mNewsBean;
    public NewsMultiItem(NewsInfo newsBean) {
        mNewsBean = newsBean;
    }

    public NewsInfo getNewsBean() {
        return mNewsBean;
    }

    public void setNewsBean(NewsInfo newsBean) {
        mNewsBean = newsBean;
    }


    @Override
    public int getItemType() {
        if(NewsUtils.isNewsPhotoSet(mNewsBean.getSkipType())){
            return ITEM_TYPE_PHOTO_SET;
        }
        return ITEM_TYPE_NORMAL;
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ITEM_TYPE_NORMAL, ITEM_TYPE_PHOTO_SET})
    public @interface NewsItemType {
    }
}
