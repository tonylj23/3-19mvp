package com.example.administrator.myapplication.module.video.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.module.base.BaseActivity;

import java.io.InputStream;

import butterknife.BindView;

import static com.example.administrator.myapplication.utils.CommonConstant.VIDEO_DATA_KEY;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoPlayerActivity extends BaseActivity<IVideoPresenter> implements IVideoView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.video_player)
    IjkPlayerView mPlayerView;
    private VideoInfo videoData;
    private VideoPlayerPresenter presenter;
    //    @BindView(R.id.iv_video_share)
//    ImageView mIvVideoShare;
//    @BindView(R.id.iv_video_collect)
//    ShineButton mIvVideoCollect;
//    @BindView(R.id.iv_video_download)
//    ImageView mIvVideoDownload;
//    @BindView(R.id.ll_operate)
//    LinearLayout mLlOperate;
//    @BindView(R.id.et_content)
//    EditText mEtContent;
//    @BindView(R.id.ll_edit_layout)
//    LinearLayout mLlEditLayout;
//    @BindView(R.id.sb_send)
//    SimpleButton mSbSend;

    public static void launch(Context context, VideoInfo data) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(VIDEO_DATA_KEY, data);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }
    @Override
    public void finishRefresh() {

    }

    @Override
    public void loadData(VideoInfo data) {
        videoData=data;

    }

    @Override
    public void loadDanmakuData(InputStream inputStream) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mPlayerView.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mPlayerView.handleVolumeKey(keyCode)){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    protected void updateView(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    protected void initView() {
        videoData = getIntent().getParcelableExtra(VIDEO_DATA_KEY);
        initToolBar(mToolbar, true, videoData.getTitle());
        presenter = new VideoPlayerPresenter(this, videoData);
        mPlayerView.init()
                .setTitle(videoData.getTitle())
                .setVideoSource(null,videoData.getMp4_url(),videoData.getMp4Hd_url(),null,null);
        Glide.with(this).load(videoData.getCover()).fitCenter().into(mPlayerView.mPlayerThumb);

    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_video_player;
    }
}
