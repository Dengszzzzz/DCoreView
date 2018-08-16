package com.fykj.dcoreview.model.TestAndVerify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.view.custom.MyView;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/8/3.
 * 自定义view 测试类
 */

public class CustomViewActivity extends BaseActivity {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.myView)
    MyView mMyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_custom_view);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("简单验证自定义view和viewGroup");

        mMyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KLog.d(TAG,"onTouch");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        KLog.d(TAG, "onTouch 的 ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                      //  KLog.d(TAG, "onTouch 的 ACTION_MOVE" + "  x:" +  event.getX() + "  y:" + event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        KLog.d(TAG, "onTouch 的 ACTION_UP");
                        break;
                }
                return true;
            }
        });
    }
}
