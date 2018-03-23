package com.example.administrator.myapplication.api;

import android.support.annotation.NonNull;

import com.example.administrator.myapplication.AndroidApplication;
import com.example.administrator.myapplication.api.bean.NewsDetailInfo;
import com.example.administrator.myapplication.api.bean.NewsInfo;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.api.bean.WelfarePhotoList;
import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.utils.NetUtil;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class RetrofitService {
    private static final String HEAD_LINE_NEWS = "T1348647909107";

    static final long CACHE_STALE_SEC=60*60*24*1;
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static final String NEWS_HOST = "http://c.3g.163.com/";
    private static final String WELFARE_HOST = "http://gank.io/";
    private static final int INCREASE_PAGE = 20;
    private static INewsApi newsApi;
    private static IWelfareApi iWelfareApi;

    private RetrofitService(){
        throw new AssertionError();
    }
    public static void init(){
        Cache cache = new Cache(new File(AndroidApplication.getAppContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NEWS_HOST)
                .build();
        newsApi = retrofit.create(INewsApi.class);


         retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(WELFARE_HOST)
                .build();
        iWelfareApi = retrofit.create(IWelfareApi.class);
    }

    private static final Interceptor sLoggingInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Buffer buffer = new Buffer();
            if(request.body()!=null){
                request.body().writeTo(buffer);
            }else{
                Logger.d("LogTag","request.boty()==null");
            }

            Logger.w(request.url()+(request.body()!=null?"?"+_parseParams(request.body(),buffer):""));
            Response proceed = chain.proceed(request);
            return proceed;
        }
    };

    private static final Interceptor sRewriteCacheControlInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetUtil.isNetworkAvailable(AndroidApplication.getAppContext())){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);
            if(NetUtil.isNetworkAvailable(AndroidApplication.getAppContext())){
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control",cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return originalResponse.newBuilder()
                        .header("Cache-Control","public,"+CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer buffer) throws UnsupportedEncodingException {
        if(body.contentType()!=null&&!body.contentType().toString()
                .contains("multipart")){
            return URLDecoder.decode(buffer.readUtf8(),"UTF-8");
        }
        return null;
    }

    public static Observable<List<NewsInfo>> getNewsList(final String newsId, int page){
        String type;
        if(HEAD_LINE_NEWS.equals(newsId)){
            type="headline";
        }else {
            type="list";
        }
//        Observable<Map<String, List<NewsInfo>>> newsList = newsApi.getNewsList(type, newsId, page * INCREASE_PAGE);
        return  newsApi.getNewsList(type, newsId, page * INCREASE_PAGE)
                .observeOn(Schedulers.io())
                .flatMap(new Function<Map<String, List<NewsInfo>>, ObservableSource<List<NewsInfo>>>() {
                    @Override
                    public ObservableSource<List<NewsInfo>> apply(Map<String, List<NewsInfo>> stringListMap) throws Exception {
                        return Observable.just(stringListMap.get(newsId));
                    }
                });

//                newsList.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<Map<String, List<NewsInfo>>, ObservableSource<NewsInfo>>() {
//                    @Override
//                    public ObservableSource<NewsInfo> apply(Map<String, List<NewsInfo>> stringListMap) throws Exception {
//                        return Observable.fromIterable(stringListMap.get(newsId));
//                    }
//                });
    }

    public static Observable<NewsDetailInfo> getNewsDetail(final String newsId){
        return newsApi.getNewsDetail(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, NewsDetailInfo>, ObservableSource<NewsDetailInfo>>() {
                    @Override
                    public ObservableSource<NewsDetailInfo> apply(Map<String, NewsDetailInfo> stringNewsDetailInfoMap) throws Exception {
                        return Observable.just(stringNewsDetailInfoMap.get(newsId));
                    }
                });
    }

    public static Observable<List<VideoInfo>> getVideoList(final String videoId, int page){
        Observable<Map<String, List<VideoInfo>>> videoList = newsApi.getVideoList(videoId, page * INCREASE_PAGE / 2);
        return videoList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, List<VideoInfo>>, ObservableSource<List<VideoInfo>>>() {
                    @Override
                    public ObservableSource<List<VideoInfo>> apply(Map<String, List<VideoInfo>> stringListMap) throws Exception {
                        return Observable.just(stringListMap.get(videoId));
                    }
                });
    }

    public static Observable<WelfarePhotoInfo> getWelfarePhoto(int page){
        return iWelfareApi.getWelfarePhoto(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<WelfarePhotoList, ObservableSource<WelfarePhotoInfo>>() {
                    @Override
                    public ObservableSource<WelfarePhotoInfo> apply(WelfarePhotoList welfarePhotoList) throws Exception {
                        if(welfarePhotoList.getResults().size()==0){
                            return Observable.empty();
                        }
                        return Observable.fromIterable(welfarePhotoList.getResults());
                    }
                });
    }
}
