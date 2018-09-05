package com.fykj.dcoreview.model.TestAndVerify;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.TestAndVerify.imageSummary.GlideActivity;
import com.fykj.dcoreview.model.TestAndVerify.netSummary.NetActivity;
import com.fykj.dcoreview.model.TestAndVerify.storageSummary.StorageActivity;
import com.fykj.dcoreview.model.TestAndVerify.viewAndAnimation.AnimationActivity;
import com.fykj.dcoreview.model.TestAndVerify.viewAndAnimation.CustomViewActivity;
import com.fykj.dcoreview.model.TestAndVerify.viewAndAnimation.ViewAndAnimActivity;

/**
 * Created by dengzh on 2018/4/18.
 * 基础知识的验证
 * 只涉及到android源代码提供的知识，不涉及第三方相关技术的探讨
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
        addClazzBean("Android 注解说明", AnnotationActivity.class);
        addClazzBean("自定义view，anim 总结", ViewAndAnimActivity.class);
        addClazzBean("数据存储知识",StorageActivity.class);
        addClazzBean("网络知识", NetActivity.class);
        addClazzBean("图片知识", GlideActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
