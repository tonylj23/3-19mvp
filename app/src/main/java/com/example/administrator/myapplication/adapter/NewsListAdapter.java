package com.example.administrator.myapplication.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.item.NewsMultiItem;
import com.example.administrator.myapplication.api.NewsUtils;
import com.example.administrator.myapplication.api.bean.NewsInfo;
import com.example.administrator.myapplication.module.news.article.NewsArticleActivity;
import com.example.administrator.myapplication.utils.DefIconFactory;
import com.example.administrator.myapplication.utils.ImageLoader;
import com.example.administrator.myapplication.utils.StringUtils;
import com.example.administrator.myapplication.widget.RippleView;
import com.flyco.labelview.LabelView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class NewsListAdapter extends BaseMultiItemQuickAdapter<NewsMultiItem,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsListAdapter(List<NewsMultiItem> data) {
        super(data);
        addItemType(NewsMultiItem.ITEM_TYPE_NORMAL, R.layout.adapter_news_list);
        addItemType(NewsMultiItem.ITEM_TYPE_PHOTO_SET, R.layout.adapter_news_photo_set);
    }


    @Override
    protected void addItemType(int type, int layoutResId) {
        super.addItemType(type, layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, NewsMultiItem item) {
        switch (item.getItemType()){
            case NewsMultiItem.ITEM_TYPE_NORMAL:
                _handleNewsNormal(helper,item.getNewsBean());
                break;
            case NewsMultiItem.ITEM_TYPE_PHOTO_SET:
                _handleNewsPhotoSet(helper,item.getNewsBean());
                break;
        }
    }

    private void _handleNewsPhotoSet(BaseViewHolder helper, NewsInfo newsBean) {
    }

    private void _handleNewsNormal(BaseViewHolder holder, final NewsInfo item) {
        ImageView newsIcon = holder.getView(R.id.iv_icon);
        ImageLoader.loadCenterCrop(mContext, item.getImgsrc(), newsIcon, DefIconFactory.provideIcon());
        holder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_source, StringUtils.clipNewsSource(item.getSource()))
                .setText(R.id.tv_time, item.getPtime());
        // 设置标签
        if (NewsUtils.isNewsSpecial(item.getSkipType())) {
            LabelView labelView = holder.getView(R.id.label_view);
            labelView.setVisibility(View.VISIBLE);
            labelView.setText("专题");
        } else {
            holder.setVisible(R.id.label_view, false);
        }
        // 波纹效果
        RippleView rippleLayout = holder.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (NewsUtils.isNewsSpecial(item.getSkipType())) {
//                    SpecialActivity.launch(mContext, item.getSpecialID());
                } else {
                    // 旧的实现方式和网易的比较相近，感兴趣的可以切换看看
//                    NewsDetailActivity.launch(mContext, item.getPostid());
                    NewsArticleActivity.launch(mContext, item.getPostid());
                }
            }
        });
    }
}
