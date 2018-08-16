package com.fykj.dcoreview.model.TestAndVerify;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fykj.dcoreview.R;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/7/3.
 * 1.activity的生命周期验证，且默认情况下横竖屏切换都是重建一次activity
 * 2.横竖屏切换不创建activity的方法
 * 3.横竖屏布局的使用
 */

public class OrientationActivity extends AppCompatActivity {

    TextView descTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_orientaition);
        ButterKnife.bind(this);
        KLog.e("onCreate()");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        KLog.e("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.e("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.e("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        KLog.e("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.e("onDestroy()");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        KLog.e("onRestart()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String string = "activity 被系统回收了怎么办？";
        outState.putString("Activity", string);
        KLog.e("onSaveInstanceState:" + string);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String string = savedInstanceState.getString("Activity");
        KLog.e("onRestoreInstanceState:" + string);
    }

    /**
     * AndroidManifest，配置configChanges 可使切屏不重建activity，通过这个方法监听切屏方向做相应处理
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 当前为横屏
            KLog.e("ORIENTATION_LANDSCAPE=" + Configuration.ORIENTATION_LANDSCAPE);// 2-横屏
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 当前为竖屏
            KLog.e("ORIENTATION_PORTRAIT=" + Configuration.ORIENTATION_PORTRAIT);// 1 - 竖屏
        }

        //1.布局分别在layout-land和layout-port目录中的同名main.xml时
        setContentView(R.layout.ac_orientaition);
        initView();  //需要重新设置控件id
        //2.布局为不按照layout-land和layout-port目录，而自定义名字时，可以判断横竖屏来使用何种布局
        //3.布局横竖屏都是一个布局时，可以直接对原有控件进行调用操作

    }

    private void initView(){
        descTv = findViewById(R.id.descTv);
        descTv.setText("......");
    }
}
