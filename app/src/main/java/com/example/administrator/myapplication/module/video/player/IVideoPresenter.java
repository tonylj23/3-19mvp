package com.example.administrator.myapplication.module.video.player;

import com.example.administrator.myapplication.local.table.DanmakuInfo;
import com.example.administrator.myapplication.module.base.ILocalPresenter;

/**
 * Created by lijunc on 2018/3/22.
 */

public interface IVideoPresenter extends ILocalPresenter{
    void addDanmaku(DanmakuInfo danmakuInfo);

    void cleanDanmaku();
}
