package com.fykj.dcoreview.model.TestAndVerify.viewAndAnimation;

import com.fykj.dcoreview.base.BaseListShowActivity;

/**
 * Created by administrator on 2018/8/16.
 * 目标：
 * View和动画的基础知识
 * 能够自定义view和熟练使用动画操作
 */
public class ViewAndAnimActivity extends BaseListShowActivity {


    @Override
    protected void initUI() {
        tvTitle.setText("自定义view，anim 总结");
    }

    @Override
    protected void initData() {
        addClazzBean("自定义控件 总结", CustomViewActivity.class);
        addClazzBean("逐帧,补间，属性动画对比",AnimationActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
