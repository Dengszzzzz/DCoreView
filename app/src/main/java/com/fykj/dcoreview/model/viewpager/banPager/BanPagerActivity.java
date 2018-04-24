package com.fykj.dcoreview.model.viewpager.banPager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.utils.ToastUtils;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/23.
 */

public class BanPagerActivity extends BaseActivity {

    @BindView(R.id.bannerView)
    RollPagerView bannerView;

    private BannerAdapter mBannerAdapter;
    private List<BannerBean> mBannerList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_ban_pager);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("滚动banner条");
        init();
    }

    private void init(){


        mBannerList.add(new BannerBean(1,"http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png",""));
        mBannerList.add(new BannerBean(2, "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg",""));
        mBannerList.add(new BannerBean(3, "http://img.zcool.cn/community/018d4e554967920000019ae9df1533.jpg@900w_1l_2o_100sh.jpg",""));


        mBannerAdapter = new BannerAdapter(this,mBannerList);
        bannerView.setAdapter(mBannerAdapter);

        //设置指示器的样式
        bannerView.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.WHITE));
        bannerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showToast("点击了" + position);
            }
        });

    }
}
