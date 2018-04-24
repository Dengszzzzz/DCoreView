package com.fykj.dcoreview.model.viewpager;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.viewpager.banPager.BanPagerActivity;
import com.fykj.dcoreview.model.viewpager.tabpager.TabPagerActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class ViewPagerListActivity extends BaseListShowActivity{
    @Override
    protected void initUI() {
        tvTitle.setText("viewpager示例");
    }

    @Override
    protected void initData() {
        addClazzBean("TabLayout，ViewPager，Fragment",TabPagerActivity.class);
        addClazzBean("广告条 viewpager",BanPagerActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
