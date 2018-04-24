package com.fykj.dcoreview.model.dialog;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.utils.ToastUtils;

/**
 * Created by dengzh on 2018/4/18.
 */

public class DialogListActivity extends BaseListShowActivity{
    @Override
    protected void initUI() {
        tvTitle.setText("Dialog示例");
    }

    @Override
    protected void initData() {
        addClazzBean("DialogFragment试用", DialogFrActivity.class);
        addClazzBean("DialogUtils 示例", DialogUActivity.class);
        mAdapter.notifyDataSetChanged();
    }

}
