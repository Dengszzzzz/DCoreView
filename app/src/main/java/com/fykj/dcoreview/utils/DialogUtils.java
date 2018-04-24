package com.fykj.dcoreview.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.model.dialog.IDialogSelect;
import com.fykj.dcoreview.view.dialog.BaseDialog;

/**
 * Created by dengzh on 2018/4/23.
 */

public class DialogUtils {


    /**
     * 显示两按钮无标题对话框
     * @param context
     * @param content
     * @param confirmText
     * @param dialogSelect
     */
    public static void showTwoButtonNotTitleDialog(Activity context, String content, String confirmText, boolean cancelOnTouchOutside, boolean cancelable, final IDialogSelect dialogSelect){
        //1.创建dialog
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_two_button_not_title, Gravity.CENTER, cancelOnTouchOutside);
        dialog.setMatchWidthDialog();
        dialog.setCancelable(cancelable);
        //2.配置信息
        dialog.setText(R.id.contentTv, content)
                .setText(R.id.confirmTv,confirmText);
        dialog.setOnViewClick(R.id.closeIv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.cancelTv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.confirmTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(1);
                }
                dialog.toggleDialog();
            }
        });
        //3.显示dialog
        dialog.toggleDialog();
    }


    /**
     * 显示两按钮有标题对话框
     * @param context
     * @param title      标题
     * @param content    内容
     * @param confirmText   确认文字
     * @param dialogSelect
     */
    public static void showTwoButtonTitleDialog(Activity context,String title,String content,String confirmText,final IDialogSelect dialogSelect){
        //1.创建dialog
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_two_button_title, Gravity.CENTER, true);
        dialog.setMatchWidthDialog();
        //2.配置信息
        dialog.setText(R.id.titleTv, title)
                .setText(R.id.contentTv,content)
                .setText(R.id.confirmTv,confirmText);
        dialog.setOnViewClick(R.id.closeIv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.cancelTv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.confirmTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(1);
                }
                dialog.toggleDialog();
            }
        });
        //3.显示dialog
        dialog.toggleDialog();
    }

}
