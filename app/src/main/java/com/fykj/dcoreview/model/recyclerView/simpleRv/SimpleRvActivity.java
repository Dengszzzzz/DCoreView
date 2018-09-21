package com.fykj.dcoreview.model.recyclerView.simpleRv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.view.recyclerView.layoutManager.SimpleLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/9/13.
 */
public class SimpleRvActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SimpleAdapter mSimpleAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_simple_rv);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("简单的自定义LayoutManager");
        init();
    }


    private void init(){
        for (int i = 0; i < 30; i++) {
            mList.add(i+"");
        }
        mSimpleAdapter = new SimpleAdapter(mList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new SimpleLayoutManager());
        mRecyclerView.setAdapter(mSimpleAdapter);
    }

    class SimpleAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public SimpleAdapter(@Nullable List<String> data) {
            super(R.layout.item_simple,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.descTv,item);
        }
    }
}
