package com.fykj.dcoreview.model.customview;

import android.view.View;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.TestAndVerify.TestAndVerifyActivity;
import com.fykj.dcoreview.model.dialog.DialogListActivity;
import com.fykj.dcoreview.model.fingerprint.FingerPrintActivity;
import com.fykj.dcoreview.model.keeplive.KeepServiceActivity;
import com.fykj.dcoreview.model.popwindow.PopWindowListActivity;
import com.fykj.dcoreview.model.recyclerView.RvListActivity;
import com.fykj.dcoreview.model.viewpager.ViewPagerListActivity;
import com.fykj.dcoreview.model.webview.WebListActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class CustomListActivity extends BaseListShowActivity{

    @Override
    protected void initUI() {
        tvTitle.setText("自定义View");
    }

    @Override
    protected void initData() {
        addClazzBean("步骤指示器", VerticalStepViewActivity.class);

        mAdapter.notifyDataSetChanged();
    }


}
