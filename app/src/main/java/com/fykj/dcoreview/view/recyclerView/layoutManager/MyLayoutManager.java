package com.fykj.dcoreview.view.recyclerView.layoutManager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by administrator on 2018/9/12.
 * 讲解LayoutManager，要从RecyclerView开始分析。
 * 1.RecyclerView.setLayoutManager()中，可看到调用了requestLayout()方法，因此会执行View树的重绘，onMeasure、onLayout、onDraw。
 * 2.RecyclerView.onLayout()中的dispatchLayout()，是布局的调用方法，在这个方法里可以看到mLayout.onLayoutChildren(mRecycler, mState)，这是我们要重写的方法。
 * 3.onLayoutChildren()，做了回收所有View，计算布局需要的各种数据，以及调用fill()来回收和布局View的处理。
 * 4.fill(),计算剩余空间，安排下一个View显示的位置，调用layoutChunk()。
 * 5.next() -> RecyclerView.getViewForPosition()。
 *   LayoutManager持有缓存mScrapList。
 *   RecyclerView持有缓存Recycler。
 * 6.RecyclerView.onTouchEvent(),是左右滑动还是垂直滑动，靠LayoutManager.canScrollHorizontally和LayoutManager.canScrollVertically决定。
 *   再交给RecyclerView.scrollByInternal()处理，最终调用了mLayout.scrollHorizontallyBy()或mLayout.scrollVerticallyBy()。
 *   滑动多少距离由LayoutManager决定，可以重写scrollHorizontallyBy()或scrollVerticallyBy()。
 *
 *
 * 自定义LayoutManager主要完成三件事：
 *   计算每个itemView的位置
 *   处理滑动事件
 *   缓存并重用itemView
 */
public class MyLayoutManager extends RecyclerView.LayoutManager {

    /**
     * 假设我们平时调用LayoutInflate.inflate(resource , null, false)第二个参数传的是null或者直接使用View.inflate()
     * 去填充View的时候那么填充的View是没有布局参数的，那么当我们的Recyclerview去addView()时就会进行判断，
     * 如果childView的布局参数为null就是调用这个方法去生成一个默认的布局参数。
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     *
     * 如果想自定义LayoutManager的话，那么必须重写onLayoutChildren方法布局子view,还不要忘了将子view在合适的时机加入的缓存中。
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        //回收所有的view
        // 计算布局需要的各种数据：
        // 布局view  回收view
       // fill();
    }

    /**
     *
     * 填充view  回收view
     *
     * @param recycler
     * @param layoutState
     * @param state
     * @param stopOnFocusable
     * @return 实际消耗的偏移量 / 实际移动的距离
     */
    private int fill(RecyclerView.Recycler recycler, LayoutState layoutState,
                     RecyclerView.State state, boolean stopOnFocusable) {
        return 0;
    }

    /**
     * 是否可以垂直滑动
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 垂直滑动的具体实现
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 根据偏移量 计算开始布局的坐标点 各种数据
        // 布局view  回收view
        // 计算实际消耗的偏移量
        // 平移children
        //返回实际消耗的偏移量
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    /**
     * 当View超出剩余空间时，保持暂时状态的帮助类
     */
    static class LayoutState {

    }
}
