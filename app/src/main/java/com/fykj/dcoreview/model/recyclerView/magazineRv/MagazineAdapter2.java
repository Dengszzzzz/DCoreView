package com.fykj.dcoreview.model.recyclerView.magazineRv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.App;
import com.fykj.dcoreview.view.recyclerView.MagazineConfig;
import com.fykj.dcoreview.view.recyclerView.MagazineView;

import java.util.List;

/**
 * Created by administrator on 2018/7/2.
 */

public class MagazineAdapter2 extends RecyclerView.Adapter<BaseViewHolder>{

    private Context mContext;
    private List<MagazineBean> mList;

    public MagazineAdapter2(Context mContext,List<MagazineBean> list) {
        this.mContext = mContext;
        mList = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_magazine,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder helper, final int position) {
        final MagazineView mv = helper.getView(R.id.mv);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mv.getLayoutParams();
        params.width = App.mzConfig.getItemWidth();
        params.height = App.mzConfig.getItemWidth();
        params.setMargins(0,0,App.mzConfig.getItemMargin(),0);
        mv.setLayoutParams(params);

        mv.setTag(helper.getAdapterPosition());
        if(position>=App.mzConfig.getBlankItemNum()){
            mv.setData(mList.get(position-App.mzConfig.getBlankItemNum()).getValue());
            mv.setVisibility(View.VISIBLE);
        }else{
            //只是显示空白占位使用,不显示内容
            mv.setVisibility(View.INVISIBLE);
        }
        mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onItemClick(mv,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size() + App.mzConfig.getBlankItemNum();
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
