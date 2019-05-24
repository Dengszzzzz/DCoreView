package com.fykj.dcoreview.model.dialog;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.model.dialog.dialogFragment.CustomDialogFragment;
import com.fykj.dcoreview.model.dialog.dialogFragment.SelectPhotoDialogFragment;
import com.fykj.dcoreview.model.dialog.dialogFragment.TestDialogFragment;
import com.fykj.dcoreview.utils.ToastUtils;
import com.fykj.dcoreview.utils.glideUtils.GlideUtils;
import com.fykj.dcoreview.utils.imageUtils.BitmapCompressUtils;
import com.fykj.dcoreview.utils.imageUtils.UriToPathUtils;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/4/19.
 * 演示 DialogFragment
 */

public class DialogFrActivity extends BaseActivity implements TestDialogFragment.DataCallback {

    @BindView(R.id.photoIv)
    ImageView photoIv;

    private SelectPhotoDialogFragment photoDialogFragment;
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
                new TestDialogFragment().show(getFragmentManager(), "dialog_fragment");
                break;
            case R.id.btPop1:
                showCustomDialogFr();
                break;
            case R.id.btPop2:
                showPhotoDialogFragment();
                break;
        }
    }


    /**
     * DialogFragment与Activity的简单交互
     *
     * @param data
     */
    @Override
    public void getTestData(String data) {
        KLog.e("test------" + data);
    }


    /**
     * 显示CustomDialogFragment
     */
    private void showCustomDialogFr() {
        if (customDialogFr == null) {
            customDialogFr = CustomDialogFragment.newInstance("支付", "月收益值100元", "确认支付");
            customDialogFr.setDialogSelect(new IDialogSelect() {
                @Override
                public void onSelected(int i, Object o) {
                    switch (i) {
                        case -1:
                            ToastUtils.showToast(i + "");
                            break;
                        case 0:
                            ToastUtils.showToast(i + "");
                            break;
                        case 1:
                            ToastUtils.showToast(i + "");
                            break;
                    }
                }
            });
        }
        customDialogFr.show(fragmentManager, "CustomDialogFr");
    }

    /**
     * 显示SelectPhotoDialogFragment
     */
    private void showPhotoDialogFragment() {
        if (photoDialogFragment == null) {
            photoDialogFragment = SelectPhotoDialogFragment.newInstance(1);
            photoDialogFragment.setDialogSelect(new IDialogSelect() {
                @Override
                public void onSelected(int i, Object o) {
                    switch (i) {
                        case 1:  //拍照
                        case 2:  //从图库选择图片
                        case 3:  //剪切图
                            //目前返回的都是uri的路径
                            String imageUri = (String) o;
                            if (!TextUtils.isEmpty(imageUri)) {
                                String path = UriToPathUtils.getImageAbsolutePath(DialogFrActivity.this, Uri.parse(imageUri));
                                Bitmap bitmap = BitmapCompressUtils.getCompressBitmap(path);
                                KLog.e("BitmapCompressUtils", "压缩后图片的大小" + (bitmap.getByteCount() / 1024 + "KB"));
                                if (bitmap != null) {
                                    GlideUtils.loadImg(DialogFrActivity.this, photoIv, imageUri);
                                }
                                bitmap.recycle();
                                bitmap = null;
                            }
                            break;
                    }
                }
            });
        }
        photoDialogFragment.show(fragmentManager, "photoDialogFragment");
    }


}
