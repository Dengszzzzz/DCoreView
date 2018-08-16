package com.fykj.dcoreview.model.TestAndVerify;

import com.fykj.dcoreview.base.BaseListShowActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class TestAndVerifyActivity extends BaseListShowActivity{


    @Override
    protected void initUI() {
        tvTitle.setText("基础知识的验证");
    }

    @Override
    protected void initData() {
        addClazzBean("activity生命周期、横竖屏切换",OrientationActivity.class);
        addClazzBean("Service总结",ServiceTestActivity.class);
        addClazzBean("Android 注解", AnnotationActivity.class);
        addClazzBean("自定义控件 总结", CustomViewActivity.class);
        addClazzBean("逐帧,补间，属性动画对比",AnimationActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
