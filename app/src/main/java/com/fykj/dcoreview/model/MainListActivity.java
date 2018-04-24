package com.fykj.dcoreview.model;

import android.view.View;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.dialog.DialogListActivity;
import com.fykj.dcoreview.model.keeplive.KeepServiceActivity;
import com.fykj.dcoreview.model.popwindow.PopWindowListActivity;
import com.fykj.dcoreview.model.viewpager.ViewPagerListActivity;

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
        addClazzBean("保活service",KeepServiceActivity.class);
        mAdapter.notifyDataSetChanged();
    }


}