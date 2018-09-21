package com.fykj.dcoreview.model.recyclerView.magazineRv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.App;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.view.recyclerView.MagazineRecyclerView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/7/2.
 */

public class MagazineActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    MagazineRecyclerView recyclerView;

    private MagazineAdapter adapter;
    private List<MagazineBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_magazine);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("rv滑动 卡片效果");
        initView();
    }

    private void initView() {
        //数据集
        for(int i = 0;i<App.mzConfig.getBlankItemNum();i++){
            mList.add(new MagazineBean(-1,""));
        }
        for (int i = 0; i < 10; i++) {
            mList.add(new MagazineBean(0, i + ""));
        }
        for(int i = 0;i<App.mzConfig.getBlankItemNum();i++){
            mList.add(new MagazineBean(-1,""));
        }

        adapter = new MagazineAdapter(mList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KLog.e("currentPage", "currentPage" + (position - App.mzConfig.getBlankItemNum()));
                recyclerView.setCurrentPage(position - App.mzConfig.getBlankItemNum());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setCurrentPage(0);

        recyclerView.setCurrentItemListener(new MagazineRecyclerView.CurrentItemListener() {
            @Override
            public void onCurrentItemStop(int position, View viewSelected) {
                KLog.e("onCurrentItemStop:" + position);
            }

            @Override
            public void onCurrentItemChange(int position, View viewSelected) {
                KLog.e("onCurrentItemChange:" + position);
            }
        });


    }
}
