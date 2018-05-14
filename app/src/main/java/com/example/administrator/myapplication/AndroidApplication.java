package com.example.administrator.myapplication;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.example.administrator.myapplication.api.RetrofitService;
import com.example.administrator.myapplication.local.dao.NewsTypeDao;
import com.example.administrator.myapplication.local.table.DaoMaster;
import com.example.administrator.myapplication.local.table.DaoSession;
import com.example.administrator.myapplication.local.table.NewsTypeInfo;
import com.example.administrator.myapplication.utils.GsonHelper;
import com.example.administrator.myapplication.utils.RxBus;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import java.util.logging.Logger;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class AndroidApplication extends Application {
    private static final String DB_NAME="news-db";
    private static Context appContext;
    private static AndroidApplication instance;
    private static String testString1234567;

    private RxBus mRxBus= RxBus.getIntanceBus();
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
        instance=this;
        _initDatabase();
        _initConfig();
    }

    public static AndroidApplication getInstance(){
        return instance;
    }
    private void _initConfig() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        com.orhanobut.logger.Logger.init("LogTAG");
        RetrofitService.init();
    }


    public static Context getAppContext(){
        return appContext;
    }
    private void _initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
//        Database db = helper.getEncryptedWritableDb("123");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        NewsTypeDao.updateLocalData(this,daoSession);
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public RxBus getmRxBus(){
        return mRxBus;
    }

//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
//        this.registerActivityLifecycleCallbacks(callback);
//    }
}
