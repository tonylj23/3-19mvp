package com.example.administrator.myapplication.module.video.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.VideoListAdapter;
import com.example.administrator.myapplication.local.table.VideoInfo;
import com.example.administrator.myapplication.module.base.BaseFragment;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.module.base.IBaseView;
import com.example.administrator.myapplication.module.base.ILoadDataView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lijunc on 2018/3/22.
 */

public class VideoListFragment extends BaseFragment<IBasePresenter> implements ILoadDataView<List<VideoInfo>> {
    private static final String VIDEO_ID_KEY = "VideoIdKey";
    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    private String videoId;
    private VideoListPresenter listPresenter;
    private VideoListAdapter videoListAdapter;

    public static VideoListFragment newInstance(String videoId){
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_ID_KEY,videoId);
        videoListFragment.setArguments(bundle);
        return videoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            videoId = getArguments().getString(VIDEO_ID_KEY);
        }
    }

    @Override
    public void loadData(List<VideoInfo> data) {
        videoListAdapter.addData(data);
    }

    @Override
    public void loadMoreData(List<VideoInfo> data) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    protected void initView() {
        videoListAdapter = new VideoListAdapter(R.layout.adapter_video_list);
        listPresenter = new VideoListPresenter(this,videoId);
        mRvPhotoList.setAdapter(videoListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        view.setHasFixedSize(true);
        mRvPhotoList.setLayoutManager(layoutManager);
        mRvPhotoList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        listPresenter.getData(isRefresh);
    }

    @Override
    protected int attachLayout() {
        return R.layout.fragment_photo_list;
    }
}
