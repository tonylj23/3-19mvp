package com.example.administrator.myapplication.api;



import com.example.administrator.myapplication.api.bean.NewsDetailInfo;
import com.example.administrator.myapplication.api.bean.NewsInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.administrator.myapplication.api.RetrofitService.AVOID_HTTP403_FORBIDDEN;
import static com.example.administrator.myapplication.api.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by long on 2016/8/22.
 * API 接口
 */
public interface INewsApi {

    /**
     * 获取新闻列表
     * eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     *      http://c.m.163.com/nc/article/list/T1348647909107/60-20.html
     *
     * @param type 新闻类型
     * @param id 新闻ID
     * @param startPage 起始页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsInfo>>> getNewsList(@Path("type") String type, @Path("id") String id,
                                                        @Path("startPage") int startPage);

    /**
     * 获取新闻详情
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("nc/article/{newsId}/full.html")
    Observable<Map<String,NewsDetailInfo>> getNewsDetail(@Path("newsId") String newsId);

    /**
     * 获取专题
     * eg: http://c.3g.163.com/nc/special/S1451880983492.html
     *
     * @param specialIde 专题ID
     * @return
     */

}
