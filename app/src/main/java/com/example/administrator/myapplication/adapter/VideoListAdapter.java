package com.example.administrator.myapplication.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.module.video.player.VideoPlayerActivity;
import com.example.administrator.myapplication.utils.DefIconFactory;
import com.example.administrator.myapplication.utils.ImageLoader;

import java.util.List;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo,BaseViewHolder> {


    public VideoListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoInfo item) {
        ImageView ivPhoto = helper.getView(R.id.iv_photo);
        ImageLoader.loadFitCenter(mContext,item.getCover(),ivPhoto, DefIconFactory.provideIcon());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPlayerActivity.launch(mContext, item);
            }
        });
    }

}
