package com.fykj.dcoreview.view.recyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.App;

/**
 * Created by administrator on 2018/7/2.
 */

public class MagazineView extends LinearLayout{

    private Context mContext;
    private View mView;
    private TextView valueTv;

    public MagazineView(Context context) {
        this(context,null);
    }

    public MagazineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MagazineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_magazine,this);
        valueTv = mView.findViewById(R.id.valueTv);

        //设置mView的宽高
    /*    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemRl.getLayoutParams();
        params.width = App.mzConfig.getItemWidth();
        params.height = App.mzConfig.getItemWidth();
        params.setMargins(0,0,App.mzConfig.getItemMargin(),0);
        mView.setLayoutParams(params);*/

    }

    public void setData(String value){
        valueTv.setText(value);
    }

    public void animateToBigStyle(){
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mView, "scaleX", 1f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mView, "scaleY", 1f, 1.5f);

        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();
    }


    public void animateToSmallStyle(){
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();
    }
}
