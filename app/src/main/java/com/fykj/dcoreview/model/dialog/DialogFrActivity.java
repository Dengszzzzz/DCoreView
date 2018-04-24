package com.fykj.dcoreview.model.dialog;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.model.dialog.dialogFragment.CustomDialogFragment;
import com.fykj.dcoreview.model.dialog.dialogFragment.TestDialogFragment;
import com.fykj.dcoreview.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/4/19.
 * 演示 DialogFragment
 */

public class DialogFrActivity extends BaseActivity implements TestDialogFragment.DataCallback{

    private CustomDialogFragment customDialogFr;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dialog_fr);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("DialogFragment示例");
        fragmentManager = getFragmentManager();
    }

    @OnClick({R.id.btPop, R.id.btPop1, R.id.btPop2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btPop:
                new TestDialogFragment().show(getFragmentManager(),"dialog_fragment");
                break;
            case R.id.btPop1:
                showCustomDialogFr();
                break;
            case R.id.btPop2:
                break;
        }
    }


    /**
     * DialogFragment与Activity的简单交互
     * @param data
     */
    @Override
    public void getTestData(String data) {

    }


    /**
     * 显示CustomDialogFragment
     */
    private void showCustomDialogFr(){
        if(customDialogFr == null){
            customDialogFr = CustomDialogFragment.newInstance("支付","月收益值100元","确认支付");
            customDialogFr.setDialogSelect(new IDialogSelect() {
                @Override
                public void onSelected(int i) {
                    switch (i){
                        case -1:
                            ToastUtils.showToast(i+"");
                            break;
                        case 0:
                            ToastUtils.showToast(i+"");
                            break;
                        case 1:
                            ToastUtils.showToast(i+"");
                            break;
                    }
                }
            });
        }
        customDialogFr.show(fragmentManager,"CustomDialogFr");
    }
}
