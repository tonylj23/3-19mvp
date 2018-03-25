package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.module.photo.bigphoto.BigPhotoActivity;
import com.example.administrator.myapplication.utils.DefIconFactory;
import com.example.administrator.myapplication.utils.ImageLoader;
import com.example.administrator.myapplication.utils.StringUtils;

/**
 * Created by lijunc on 2018/3/23.
 */

public class WelfarePhotoAdapter extends BaseQuickAdapter<WelfarePhotoInfo, BaseViewHolder> {


    private int mPhotoWidth;

    private Context context;
    public WelfarePhotoAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context=context;
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = context.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
        mPhotoWidth = widthPixels / 2 - marginPixels;
    }


    @Override
    protected void convert(BaseViewHolder helper, final WelfarePhotoInfo item) {
        final ImageView ivPhoto = helper.getView(R.id.iv_photo);
        int photoHeight = StringUtils.calcPhotoHeight(item.getPixel(), mPhotoWidth);
        final ViewGroup.LayoutParams params = ivPhoto.getLayoutParams();
        params.width = mPhotoWidth;
        params.height = photoHeight;
        ivPhoto.setLayoutParams(params);
        ImageLoader.loadFitCenter(mContext, item.getUrl(), ivPhoto, DefIconFactory.provideIcon());
        helper.setText(R.id.tv_title, item.getCreatedAt());
//       helper.getConvertView().setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//           }
//       });
    }
}
