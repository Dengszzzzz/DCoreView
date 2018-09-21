package com.fykj.dcoreview.view.recyclerView.layoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.ViewTarget;
import com.just.agentweb.LogUtils;
import com.socks.library.KLog;
import com.socks.library.KLogUtil;

import java.lang.invoke.WrongMethodTypeException;

/**
 * Created by administrator on 2018/9/13.
 * 简单自定义LayoutManager
 * 1.detachAndScrapAttachedViews(recycler); 把所有View从RecyclerView中解绑，并放入recycler中的一个集合中
 * 2.calculateChildrenSite();  遍历Recycler中保存的View取出来，每个View的区域用Rect保存，再放入allItemRects集合中，此时并不做显示
 * 3.recycleAndFillView();     得到当前显示屏幕的Rect，以此判断哪些item需要移除，哪些需要显示。将滑出屏幕的Items回收到Recycle缓存中。
 *                             在显示范围内的item，获取Recycler中缓存的View，显示出来。
 * 4.scrollVerticallyBy();     每次都解绑全部View，后续再调用recycleAndFillView()。
 */
public class SimpleLayoutManager extends RecyclerView.LayoutManager {

    private String TAG = getClass().getSimpleName();

    /**
     * 用于保存item的位置信息
     */
    private SparseArray<Rect> allItemRects = new SparseArray<>();
    /**
     * 用于保存item是否处于可见状态的信息
     */
    private SparseBooleanArray itemStates = new SparseBooleanArray();

    private int totalHeight = 0;
    private int verticalScrollOffset;  //垂直滚动的距离

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        super.onLayoutChildren(recycler, state);
        //先把所有View从RecyclerView中detach掉，然后标记为“Scrap”状态，表示这些View处于可被重用状态（非显示中）
        //实际就是把View放到recycler中的一个集合中
        detachAndScrapAttachedViews(recycler);
        calculateChildrenSite(recycler);
        recycleAndFillView(recycler, state);
    }

    /**
     * 遍历recycler，取出View，并用Rect保存每个View的坐标点
     * @param recycler
     */
    private void calculateChildrenSite(RecyclerView.Recycler recycler) {
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //遍历Recycler中保存的View取出来
            View view = recycler.getViewForPosition(i);
            addView(view);  //由于先前进行了detach操作，所以可以重新添加。
            measureChildWithMargins(view, 0, 0);  //通知测量view的margin值
            calculateItemDecorationsForChild(view, new Rect());

            int width = getDecoratedMeasuredWidth(view);   //计算view实际大小，包括了ItemDecorator中设置得偏移量。
            int height = getDecoratedMeasuredHeight(view);

            Rect mTmpRect = allItemRects.get(i); //从缓存中取
            if (mTmpRect == null) {
                mTmpRect = new Rect();
            }
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator
            //calculateItemDecorationsForChild(view, mTmpRect);

            //指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，包括ItemDecorator设置的距离。
            //layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            mTmpRect.set( 0, totalHeight, width, totalHeight + height);
            totalHeight += height;

            //保存ItemView的位置信息
            allItemRects.put(i, mTmpRect);
            //由于之前调用过detachAndScrapAttachedViews(recycler)，所以此时item都是不可见的
            itemStates.put(i, false);

        }
      //  KLog.d(TAG, "totalHeight:" + totalHeight);
    }

    @Override
    public boolean canScrollVertically() {
        //返回true表示可以纵向滑动
        return true;
    }

    /**
     * 每次调用都解绑释放所有的View，再调用recycleAndFillView()
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //每次滑动时先释放掉所有的View，因为后面调用recycleAndFillView()时会重新addView()。
        detachAndScrapAttachedViews(recycler);

        //列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致，dy其实是某段时间的位移。
        int travel = dy;   //实际要滑动的距离
       // KLog.d(TAG, "dy = " + dy);

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;  //此时由于verticalScrollOffset为0，所以travel也应该为0
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) { //如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;  //travel会为0
        }
        //将竖直方向的偏移量 + travel，经过上面一步，只有在非顶部/非底部时，verticalScrollOffset才会改变。
        verticalScrollOffset += travel;
        //KLog.d(TAG, "travel = " + travel);
       // KLog.d(TAG, "verticalScrollOffset = " + verticalScrollOffset);

        //调用该方法通知view在y方向上移动指定距离
        offsetChildrenVertical(-travel);
        recycleAndFillView(recycler, state); //回收并显示View


        return travel;
    }

    private int getVerticalSpace() {
        //计算RecyclerView的可用高度，除去上下Padding值
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * getChildCount():由于重用，这个获取的是真实的View数量，(一般来说是可见的item数量)。
     * getItemCount():数据条目数量
     * 判断item需不需要显示的条件是，该item的Rect是否在屏幕Rect范围内。
     * @param recycler
     * @param state
     */
    private void recycleAndFillView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        //当前scroll offset状态下的显示区域,其实就是能看到的区域
        Rect displayRect = new Rect(0, verticalScrollOffset, getHorizontalSpace(), verticalScrollOffset + getVerticalSpace());

        //将滑出屏幕的Items回收到Recycle缓存中
        Rect childRect = new Rect();
        KLog.d(TAG,"前--" + getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            //这个方法获取的是RecyclerView中的View，注意区分Recycler中的View
            //这获取的是实际的View
            View child = getChildAt(i);
            //下面几个方法能够获取每个View占用的空间的位置信息，包括ItemDecorator。
            childRect.left = getDecoratedLeft(child);
            childRect.top = getDecoratedTop(child);
            childRect.right = getDecoratedRight(child);
            childRect.bottom = getDecoratedBottom(child);
            //如果Item没有在显示区域，就说明要回收
            if(!Rect.intersects(displayRect,childRect)){
                //移除并回收掉滑出屏幕的View
                removeAndRecycleView(child,recycler);
                itemStates.put(i, false); //更新该View的状态为未依附
            }
        }

        //重新显示需要出现在屏幕的子View
        for (int i = 0; i < getItemCount(); i++) {
            //判断ItemView的位置和当前区域是否重合
            if(Rect.intersects(displayRect,allItemRects.get(i))){
                //获取Recycler中缓存的View
                View itemView = recycler.getViewForPosition(i);
                measureChildWithMargins(itemView, 0, 0);
                //添加View到RecyclerView上
                addView(itemView);
                //取出先前存好的ItemView的位置矩形
                Rect rect = allItemRects.get(i);
                //将这个item布局出来
                layoutDecorated(itemView, rect.left, rect.top - verticalScrollOffset, rect.right, rect.bottom - verticalScrollOffset);
                //layoutDecoratedWithMargins(itemView,rect.left,rect.top - verticalScrollOffset,rect.right,rect.bottom - verticalScrollOffset);
                itemStates.put(i,true);  //更新该View的状态为依附。
            }
        }
        KLog.d(TAG,"后--" + getChildCount());
    }
}
