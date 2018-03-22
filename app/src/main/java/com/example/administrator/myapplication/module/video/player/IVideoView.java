package com.example.administrator.myapplication.module.video.player;

import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.module.base.IBaseView;

import java.io.InputStream;

/**
 * Created by lijunc on 2018/3/22.
 */

public interface IVideoView extends IBaseView{
    void loadData(VideoInfo data);

    void loadDanmakuData(InputStream inputStream);
}
