package com.fykj.dcoreview.model.recyclerView.simpleRv;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.view.recyclerView.layoutManager.CardItemView;
import com.fykj.dcoreview.view.recyclerView.layoutManager.CardLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by administrator on 2018/9/14.
 */
public class CardRvActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
            0xff7FFF00, 0xff6495ED, 0xffDC143C,
            0xff008B8B, 0xff006400, 0xff2F4F4F,
            0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
            0xff90EE90, 0xff87CEFA, 0xff800000};

    private CardAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private int mCount = 50;
    private int mGroupSize = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_card_rv);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("简单的自定义LayoutManager");

        for (int i = 0; i < mCount; i++) {
            mList.add(i + "");
        }
        mAdapter = new CardAdapter(mList);
        init();
    }

    private void init() {
        mRecyclerView.setLayoutManager(new CardLayoutManager(mGroupSize, true));
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.addBt, R.id.changeBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addBt:  //加入10个
                int lastPosition = mList.size();
                for (int i = 0; i < 10; i++) {
                    mList.add(lastPosition + i + "");
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.changeBt: //转换
                if (mGroupSize == 3) { mGroupSize = 9;}
                else { mGroupSize = 3;}
                init();
                break;
        }
    }

    class CardAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public CardAdapter(@Nullable List<String> data) {
            super(R.layout.item_card, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ((CardItemView) helper.getView(R.id.cardView)).setCardColor(randomColor());
            helper.setText(R.id.descTv, item);
        }

        private int randomColor() {
            return COLORS[new Random().nextInt(COLORS.length)];
        }
    }

    class CardAdapter2 extends RecyclerView.Adapter<BaseViewHolder>{

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}
