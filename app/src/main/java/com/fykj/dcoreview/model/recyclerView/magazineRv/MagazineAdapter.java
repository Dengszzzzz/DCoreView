package com.fykj.dcoreview.model.recyclerView.magazineRv;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.App;
import com.fykj.dcoreview.view.recyclerView.magazineRv.MagazineView;

import java.util.List;

/**
 * Created by administrator on 2018/7/2.
 */

public class MagazineAdapter extends BaseQuickAdapter<MagazineBean,BaseViewHolder>{

    public MagazineAdapter(@Nullable List<MagazineBean> data) {
        super(R.layout.item_magazine,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MagazineBean item) {

        MagazineView mv = helper.getView(R.id.mv);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mv.getLayoutParams();
        params.width = App.mzConfig.getItemWidth();
        params.height = App.mzConfig.getItemWidth();
        params.setMargins(0,0,App.mzConfig.getItemMargin(),0);
        mv.setLayoutParams(params);
        mv.setTag(helper.getAdapterPosition());

        if(item.getResId()==-1){   //只是用来占位
            mv.setVisibility(View.INVISIBLE);
        }else{
            mv.setData(item.getValue());
            mv.setVisibility(View.VISIBLE);
        }

    }

}
