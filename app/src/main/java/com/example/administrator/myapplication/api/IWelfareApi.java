package com.example.administrator.myapplication.api;


import com.example.administrator.myapplication.api.bean.WelfarePhotoList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.example.administrator.myapplication.api.RetrofitService.AVOID_HTTP403_FORBIDDEN;
import static com.example.administrator.myapplication.api.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by long on 2016/10/10.
 */

public interface IWelfareApi {

    /**
     * 获取福利图片
     * eg: http://gank.io/api/data/福利/10/1
     *
     * @param page 页码
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("/api/data/福利/10/{page}")
    Observable<WelfarePhotoList> getWelfarePhoto(@Path("page") int page);


}
