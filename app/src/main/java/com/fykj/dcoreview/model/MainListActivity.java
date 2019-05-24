package com.fykj.dcoreview.model;

import android.view.View;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.TestAndVerify.TestAndVerifyActivity;
import com.fykj.dcoreview.model.customview.CustomListActivity;
import com.fykj.dcoreview.model.dialog.DialogListActivity;
import com.fykj.dcoreview.model.fingerprint.FingerPrintActivity;
import com.fykj.dcoreview.model.keeplive.KeepServiceActivity;
import com.fykj.dcoreview.model.popwindow.PopWindowListActivity;
import com.fykj.dcoreview.model.recyclerView.RvListActivity;
import com.fykj.dcoreview.model.viewpager.ViewPagerListActivity;
import com.fykj.dcoreview.model.webview.WebListActivity;
import com.fykj.rxjava.RxJavaListActivity;
import com.javaSummary.module.JavaTestActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class MainListActivity extends BaseListShowActivity{

    @Override
    protected void initUI() {
        tvTitle.setText("DCoreView");
        ivBack.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        addClazzBean("Dialog", DialogListActivity.class);
        addClazzBean("PopWindow", PopWindowListActivity.class);
        addClazzBean("ViewPager", ViewPagerListActivity.class);
        addClazzBean("webView",WebListActivity.class);
        addClazzBean("保活service",KeepServiceActivity.class);
        addClazzBean("RecyclerView",RvListActivity.class);
        addClazzBean("指纹识别", FingerPrintActivity.class);
        addClazzBean("自定义View",CustomListActivity.class);
        addClazzBean("Android知识的验证",TestAndVerifyActivity.class);
        addClazzBean("Java知识的验证",JavaTestActivity.class);
        addClazzBean("RxJava2 学习",RxJavaListActivity.class);
        mAdapter.notifyDataSetChanged();
    }


}
