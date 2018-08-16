package com.fykj.dcoreview.model.TestAndVerify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.annotations.Sex;
import com.fykj.dcoreview.annotations.SexTest;
import com.fykj.dcoreview.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by administrator on 2018/7/27.
 */

public class AnnotationActivity extends BaseActivity{

    @BindView(R.id.sexTv)
    TextView sexTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_annotation);
        initTitle();
        tvTitle.setText("注解详情");

        SexTest sexTest = new SexTest();
        sexTest.setSex(Sex.MAN);
        sexTv.setText("性别:" + sexTest.getSex());
        System.out.println(sexTest.getSex() + "");
    }
}
