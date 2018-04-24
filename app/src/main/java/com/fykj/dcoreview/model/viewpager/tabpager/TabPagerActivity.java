package com.fykj.dcoreview.model.viewpager.tabpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/23.
 * TabLayout,ViewPager,fragment 演示
 */

public class TabPagerActivity extends BaseActivity{

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<EventTypeBean> mTypeList = new ArrayList<>();
    private MyFrPagerAdapter<SimpleFragment> myPagerAdapter;
    private ArrayList<SimpleFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tab_pager);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Tab和ViewPager演示");
        initData();
    }


    /**
     * 接受数据后
     * 填充tab 数据
     */
    private void initData(){
        // 封装数据
        for(int i = 0;i<6;i++){
            mTypeList.add(new EventTypeBean(i,"类型" + i,""));
        }
        fragments = new ArrayList<>();
        for(int i = 0;i<mTypeList.size();i++){
            fragments.add(SimpleFragment.newInstance(mTypeList.get(i).getId()+""));
        }
        //关联数据
        myPagerAdapter = new MyFrPagerAdapter<>(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(myPagerAdapter);
        //setupWithViewPager 会清除tab,要放在tab设置前面关联
        mTabLayout.setupWithViewPager(viewPager);
        //设置样式
        for(int i = 0;i<mTypeList.size();i++){
            mTabLayout.getTabAt(i).setCustomView(getTabView(mTypeList.get(i).getTypename(),mTypeList.get(i).getPicture()));
        }
        viewPager.setCurrentItem(0,false);
    }

    /**
     * 构造 tabview
     * @param name
     * @param url
     * @return
     */
    public View getTabView(String name, String url) {
        View view = getLayoutInflater().inflate(R.layout.item_tab_pager_top,null);
        TextView typeNameTv =  view.findViewById(R.id.typeNameTv);
        typeNameTv.setText(name);
        ImageView typeIv =  view.findViewById(R.id.typeIv);
       // GlideUtils.loadImg(this,typeIv,url);
        return view;
    }

}
