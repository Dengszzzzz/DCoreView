package com.fykj.dcoreview.model.dialog.dialogFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fykj.dcoreview.R;

/**
 * Created by dengzh on 2018/4/18.
 * 使用DialogFragment 替换 Dialog
 * 好处：解决屏幕旋转 activity重建，dialog问题
 * 与activity通信，接口回调
 */

public class TestDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    @Override
    public void onAttach(Activity activity) {
        // onAttach()是合适的早期阶段进行检查MyActivity是否真的实现了接口。
        // 采用接口的方式，dialog无需详细了解MyActivity，只需了解其所需的接口函数，这是真正项目中应采用的方式。
        if (!(activity instanceof DataCallback)) {
            throw new IllegalStateException("fragment所在的Activity必须实现Callbacks接口");
        }
        super.onAttach(activity);
    }

    //从生命周期的顺序而言，先执行onCreateDialog()，后执行onCreateView()
    //不需自定义view  可以试用onCreateDialog();
    //别同时使用onCreatView和onCreatDialog方法
  /*  @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("用户申明")
                .setMessage("你好")
                .setPositiveButton("我同意", this)
                .setNegativeButton("不同意", this)
                .setCancelable(false);
        //.show(); // show cann't be use here
        return builder.create();

    }*/

    /**
     * onCreate()的时候可以设置Dialog相关的风格和属性
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    /**
     * onCreateView（）的时候设置View相关
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_dialog_test,null);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        DataCallback callback = (DataCallback) getActivity();
        callback.getTestData("test");
    }

    public interface DataCallback {
        void getTestData(String data);
    }
}
