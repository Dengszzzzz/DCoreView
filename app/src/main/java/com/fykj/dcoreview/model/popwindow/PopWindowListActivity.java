package com.fykj.dcoreview.model.popwindow;

import com.fykj.dcoreview.base.BaseListShowActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class PopWindowListActivity extends BaseListShowActivity{
    @Override
    protected void initUI() {
        tvTitle.setText("PopWindow示例");
    }

    @Override
    protected void initData() {
        addClazzBean("BasePopWindow测试", BasePopTestActivity.class);
        mAdapter.notifyDataSetChanged();
    }


}
