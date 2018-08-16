package com.fykj.dcoreview.model.recyclerView;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.recyclerView.magazineRv.MagazineActivity;
import com.fykj.dcoreview.model.viewpager.banPager.BanPagerActivity;
import com.fykj.dcoreview.model.viewpager.tabpager.TabPagerActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class RvListActivity extends BaseListShowActivity{
    @Override
    protected void initUI() {
        tvTitle.setText("recyclerView示例");
    }

    @Override
    protected void initData() {
        addClazzBean("卡片滑动recyclerView",MagazineActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
