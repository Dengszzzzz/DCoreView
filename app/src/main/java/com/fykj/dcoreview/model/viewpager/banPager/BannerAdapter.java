package com.fykj.dcoreview.model.viewpager.banPager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fykj.dcoreview.utils.glideUtils.GlideUtils;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by dengzh on 2018/4/23.
 * Banner 适配器 rollviewpager
 */

public class BannerAdapter extends StaticPagerAdapter{

    private Context mContext;
    private List<BannerBean> mList;

    public BannerAdapter(Context mContext, List<BannerBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public View getView(ViewGroup container, final int position) {

        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        GlideUtils.loadImg(mContext,view,mList.get(position).getImgPath());
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });*/
        return view;
    }

    @Override
    public int getCount() {
        return mList == null? 0:mList.size();
    }

   /* //设置点击监听
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }*/
}
