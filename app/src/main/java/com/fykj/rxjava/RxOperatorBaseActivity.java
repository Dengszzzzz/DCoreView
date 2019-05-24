package com.fykj.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 每一种RxJava 2.x 操作符的基类
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-19  17:00
 */

public abstract class RxOperatorBaseActivity extends BaseActivity {
    @BindView(R.id.text)
    TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rx_operator_base);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText(getSubTitle());
    }


    protected abstract String getSubTitle();

    protected abstract void doSomething();


    @OnClick(R.id.btn)
    public void onViewClicked() {
        mText.append("\n");
        doSomething();
    }
}
