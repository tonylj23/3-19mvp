package com.example.administrator.myapplication.module.photo.bigphoto;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.module.base.BaseActivity;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class BigActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WelfarePhotoInfo data = (WelfarePhotoInfo) getIntent().getParcelableExtra("data");
        String url = data.getUrl();
        Log.d("BigActivity", "onCreate: url:"+url);
    }


}
