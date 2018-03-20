package com.example.administrator.myapplication.local.dao;

import android.content.Context;

import com.example.administrator.myapplication.local.table.DaoSession;
import com.example.administrator.myapplication.local.table.NewsTypeInfo;
import com.example.administrator.myapplication.local.table.NewsTypeInfoDao;
import com.example.administrator.myapplication.utils.AssetsHelper;
import com.example.administrator.myapplication.utils.GsonHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class NewsTypeDao {
    private static List<NewsTypeInfo> newsChannel;

    public NewsTypeDao() {
    }

    public static void updateLocalData(Context context, DaoSession daoSession){
        newsChannel = GsonHelper.convertEntities(AssetsHelper.readData(context, "NewsChannel"), NewsTypeInfo.class);
        NewsTypeInfoDao infoDao = daoSession.getNewsTypeInfoDao();
        if(infoDao.count()==0){
            infoDao.insertInTx(newsChannel.subList(0,3));
        }
    }

    public static List<NewsTypeInfo> getNewsChannel(){
        return newsChannel;
    }
}
