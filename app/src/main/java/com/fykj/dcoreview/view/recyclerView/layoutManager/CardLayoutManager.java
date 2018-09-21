package com.fykj.dcoreview.view.recyclerView.layoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by administrator on 2018/9/14.
 */
public class CardLayoutManager extends RecyclerView.LayoutManager{

    public static final int DEFAULT_GROUP_SIZE = 5;

    private int mGroupSize;
    private int mHorizontalOffset;
    private int mVerticalOffset;
    private int mTotalWidth;
    private int mTotalHeight;
    private int mGravityOffset;
    private boolean isGravityCenter;

    private Pool<Rect> mItemFrames;

    public CardLayoutManager(boolean center) {
        this(DEFAULT_GROUP_SIZE, center);
    }

    public CardLayoutManager(int groupSize, boolean center) {
        mGroupSize = groupSize;
        isGravityCenter = center;
        mItemFrames = new Pool<>(new Pool.New<Rect>() {
            @Override
            public Rect get() {
                return new Rect();
            }
        });
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //item数量小于0，在布局之前
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}

        //样式大小都是一致的，只要取一个就好
        detachAndScrapAttachedViews(recycler);
        View first = recycler.getViewForPosition(0);
        measureChildWithMargins(first,0,0);
        int itemWidth = getDecoratedMeasuredWidth(first);
        int itemHeight = getDecoratedMeasuredHeight(first);

        int firstLineSize = mGroupSize/2 + 1;   //每一组第一行的item个数
        int secondLineSize = firstLineSize + mGroupSize/2;  //每一组第二行的item个数
        //设置了isGravityCenter为true, 并且每组的宽度小于recyclerView的宽度时居中显示
        if(isGravityCenter && firstLineSize * itemWidth < getHorizontalSpace()){
            mGravityOffset = (getHorizontalSpace() - firstLineSize * itemWidth)/2;
        }else {
            mGravityOffset = 0;
        }

        for (int i = 0; i < getItemCount(); i++) {
            Rect item = mItemFrames.get(i);
            float coefficient = isFirstGroup(i) ? 1.5f:1f;
            int offsetHeight = (int) ((i / mGroupSize) * itemHeight * coefficient);

            //每一组的第一行
            if(isItemInFirstLine(i)){
                int offsetInLine = i < firstLineSize? i : i % mGroupSize;
                item.set(mGravityOffset + offsetInLine * itemWidth
                        , offsetHeight
                        , mGravityOffset + offsetInLine * itemWidth + itemWidth
                        , itemHeight + offsetHeight);
            }else {
                //每一组的第二行
                int lineOffset = itemHeight/2;
                int offsetInLine = (i < secondLineSize ? i : i % mGroupSize) - firstLineSize;
                item.set(mGravityOffset + offsetInLine * itemWidth + itemWidth / 2,
                        offsetHeight + lineOffset, mGravityOffset + offsetInLine * itemWidth + itemWidth  + itemWidth / 2,
                        itemHeight + offsetHeight + lineOffset);
            }
        }
        mTotalWidth = Math.max(firstLineSize * itemWidth,getHorizontalSpace());
        int totalHeight = getGroupSize() * itemHeight;
        if (!isItemInFirstLine(getItemCount() - 1)) {
            totalHeight += itemHeight / 2;
        }
        mTotalHeight = Math.max(totalHeight,getVerticalSpace());
        fill(recycler,state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state){
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}
        //屏幕显示区域
        Rect displayRect = new Rect(mHorizontalOffset, mVerticalOffset,
                getHorizontalSpace() + mHorizontalOffset,
                getVerticalSpace() + mVerticalOffset);

        for(int i=0;i<getItemCount();i++){
            //从缓存中取，再判断该itemView是否处于可见区域
            Rect frame = mItemFrames.get(i);
            if(Rect.intersects(displayRect,frame)){
                View scrap = recycler.getViewForPosition(i);
                addView(scrap);
                measureChildWithMargins(scrap,0,0);
                layoutDecorated(scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                        frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset);
            }
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if(mVerticalOffset + dy < 0){
            dy = -mVerticalOffset;
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }

        offsetChildrenVertical(-dy);
        fill(recycler,state);
        mVerticalOffset += dy;
        return dy;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(mHorizontalOffset + dx < 0) {
            dx = -mHorizontalOffset;
        } else if (mHorizontalOffset + dx > mTotalWidth - getHorizontalSpace()) {
            dx = mTotalWidth - getHorizontalSpace() - mHorizontalOffset;
        }

        offsetChildrenHorizontal(-dx);
        fill(recycler, state);
        mHorizontalOffset += dx;
        return dx;
    }

    private int getHorizontalSpace(){
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace(){
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private boolean isItemInFirstLine(int index) {
        int firstLineSize = mGroupSize / 2 + 1;
        return index < firstLineSize || (index >= mGroupSize && index % mGroupSize < firstLineSize);
    }

    /**
     * Math.cell()向上取
     * @return  分为多少组
     */
    private int getGroupSize() {
        return (int) Math.ceil(getItemCount() / (float)mGroupSize);
    }
    /**
     * 是否是第一组
     * @param index
     * @return
     */
    private boolean isFirstGroup(int index){
        return index < mGroupSize;
    }
}
