package com.example.administrator.myapplication.module.photo.welfare;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.WelfarePhotoAdapter;
import com.example.administrator.myapplication.api.bean.WelfarePhotoInfo;
import com.example.administrator.myapplication.module.base.BaseFragment;
import com.example.administrator.myapplication.module.base.IBasePresenter;
import com.example.administrator.myapplication.module.base.IBaseView;
import com.example.administrator.myapplication.module.base.ILoadDataView;
import com.example.administrator.myapplication.module.base.IRxBusPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lijunc on 2018/3/23.
 */

public class WelfareListFragment extends BaseFragment<IBasePresenter> implements ILoadDataView<List<WelfarePhotoInfo>>{

    private WelfareListPresenter presenter;
    private WelfarePhotoAdapter adapter;
    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    @Override
    public void loadData(List<WelfarePhotoInfo> data) {
        adapter.addData(data);
    }

    @Override
    public void loadMoreData(List<WelfarePhotoInfo> data) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    protected void initView() {
        presenter = new WelfareListPresenter(this);
        adapter = new WelfarePhotoAdapter(getActivity(),R.layout.adapter_welfare_photo);
        mRvPhotoList.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        view.setHasFixedSize(true);
        mRvPhotoList.setLayoutManager(layoutManager);
        mRvPhotoList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    protected int attachLayout() {
        return R.layout.fragment_photo_list;
    }
}
