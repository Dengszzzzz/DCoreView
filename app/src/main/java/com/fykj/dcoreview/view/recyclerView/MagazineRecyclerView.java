package com.fykj.dcoreview.view.recyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.App;
import com.fykj.dcoreview.view.recyclerView.magazineRv.MagazineConfig;
import com.fykj.dcoreview.view.recyclerView.magazineRv.MagazineView;
import com.socks.library.KLog;

public class MagazineRecyclerView extends RecyclerView {
    /**
     * 头部和尾部空白区域的item数
     **/
    private int mBlankItemNum;
    /**
     * item宽度
     **/
    private int mItemWidth;
    /**
     * item之间的间隔
     **/
    private int mItemMargin;
    /**
     * scroll动画时长
     */
    private int mScrollDuration;
    /**
     * 一个item所占的长度
     **/
    private int mItemMoveDistance;
    /**
     * 选中item的位置
     **/
    private int mCurrentViewPos;
    /**
     * 选中的item
     **/
    private View mCurrentView;
    /**
     * 要滚到到的scrollX
     */
    private int mTargetScrollX;
    /**
     * 上一次事件坐标
     **/
    private float mLastX = 0;
    /**
     * 滑动的坐标距离
     **/
    private float mScrollDistance = 0;
    /**
     * 选中项改变回调
     */
    private CurrentItemListener mCurrentItemListener;
    /**
     * 大视图是否在Move过程就已经改变了，如果改变了则Up就不再改变了
     */
    boolean isHighViewChangedByMove;
    /**
     * 在Move或Up中大视图是否改变了，如果改变了，则在Up后将大视图移至中间
     */
    boolean isHighViewChanged;
    /**
     * scroll动画
     */
    private ValueAnimator mAnimator;
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;

    public MagazineRecyclerView(Context context) {
        this(context, null, 0);
    }

    public MagazineRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagazineRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化配置
        MagazineConfig config = App.mzConfig;
        mItemMargin = config.getItemMargin();
        mItemWidth = config.getItemWidth();
        mScrollDuration = config.getScrollDuration();
        //左右间隔item数量
        mBlankItemNum = config.getBlankItemNum();
        //item移动距离
        mItemMoveDistance = mItemWidth + mItemMargin;

        mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                scrollBy((int) ((mTargetScrollX - getScollXDistance()) * value), 0);
            }
        };
        mAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mScrollDuration);
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    /**
     * 设置为选中的view样式
     */
    private void setBigStyle(View view) {
        KLog.e("MagazineView:变大");
        ((MagazineView) view).animateToBigStyle();
        if(view!=null){
            int pos = (int) view.getTag();
            mCurrentViewPos = pos;
            mCurrentView = view;
        }
    }

    /**
     * 设置为没选中的View样式
     */
    private void setSmallStyle(View currentView) {
        //最开始加载时 view会为空，所以需判空
        if (currentView != null) {
            KLog.e("MagazineView:变小");
            ((MagazineView) currentView).animateToSmallStyle();
        }
    }

    /**
     * 获得item的x坐标
     *
     * @param itemIndex
     * @return
     */
    private int getItemX(int itemIndex) {
        //如果是最左侧的空白图
        if (itemIndex < mBlankItemNum) {
            return 0;
        }
        //如果是最右侧的空白图
        else if (itemIndex >= (getAdapter().getItemCount() - mBlankItemNum)) {
            return (getAdapter().getItemCount() - mBlankItemNum - 1) * mItemMoveDistance;
        }
        return (itemIndex - mBlankItemNum) * (mItemMoveDistance) + (mItemWidth / 2) + mItemMargin;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求所有父控件及祖宗控件不要拦截事件，由于首页外层还嵌了一个ScrollView，导致横向滑动不够灵敏，所以进行处理
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isHighViewChanged = false;
                isHighViewChangedByMove = false;
                mLastX = ev.getX();
                mScrollDistance = 0;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mScrollDistance += ev.getX() - mLastX;  //统计移动的距离
                mLastX = ev.getX();
                //如果移动距离 比 item规定的移动弄距离大，则标记为要改变的View
                if (Math.abs(mScrollDistance) >= mItemMoveDistance) {
                    //移动过几个itemView
                    int changeItemNum = (int) (mScrollDistance / mItemMoveDistance);
                    //真正要滚动的距离
                    mScrollDistance -= changeItemNum * mItemMoveDistance;
                    //改变为放大视图
                    changeItemToBigStyle(changeItemNum);
                    isHighViewChangedByMove = true;
                    KLog.e("Magazine","Move,changeHigh");
//                    RingLog.e("Move, changeHigh");
                }
                break;

            case MotionEvent.ACTION_UP:
                //如果移动量超过单个长度的一半，则更新相应的高亮图
                if (!isHighViewChangedByMove && Math.abs(mScrollDistance) >= mItemMoveDistance / 2) {
                    int changeItemNum = (int) (mScrollDistance / (mItemMoveDistance / 2));
                    mScrollDistance -= changeItemNum * mItemMoveDistance;
                    changeItemToBigStyle(changeItemNum);  //放大视图
                    KLog.e("Magazine","Up,changeHigh");
//                    RingLog.e("Up, changeHigh");
                }

                //把当前视图移动到中间位置
                if (isHighViewChanged) {
                    isHighViewChanged = false;
                    KLog.e("Magazine","Up, move much, ScrollToHigh");
//                    RingLog.e("Up, move much, ScrollToHigh");
                    smoothScrollToCurrentItem();
                    if (mCurrentItemListener != null) {
                        mCurrentItemListener.onCurrentItemStop(mCurrentViewPos - mBlankItemNum, mCurrentView);
                    }
                } else {
                    KLog.e("Magazine","Up, move little, ScrollToHigh");
//                    RingLog.e("Up, move little, ScrollToHigh");
                    smoothScrollToCurrentItem();
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                //释放速度检测器
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return super.fling(velocityX / 300, velocityY / 300);//减小惯性滑动速度
    }

    /**
     * 改变为大视图样式
     */
    private void changeItemToBigStyle(View view) {
        if (view != null) {
            setSmallStyle(mCurrentView);
            setBigStyle(view);
            isHighViewChanged = true;

            if (mCurrentItemListener != null) {
                mCurrentItemListener.onCurrentItemChange(mCurrentViewPos - mBlankItemNum, mCurrentView);
            }
        }

    }

    /**
     * 改变为大视图样式
     */
    private void changeItemToBigStyle(int itemNum) {
        int currentItem = mCurrentViewPos - itemNum;
        if (currentItem < mBlankItemNum || currentItem >= (getAdapter().getItemCount() - mBlankItemNum) || currentItem == mCurrentViewPos) {
            return;
        }
        changeItemToBigStyle(getItemViewByPosition(currentItem));
    }

    /**
     * 获取列表中指定位置的视图，注意，该位置的视图需为可见状态，否则获取为null
     */
    public View getItemViewByPosition(int position) {
        LayoutManager layoutManager = getLayoutManager();
        View view = layoutManager.findViewByPosition(position);
        if (view != null) {
            //return view;
            return view.findViewById(R.id.mv);
        } else return null;
    }

    /**
     * 设置选中页
     *
     * @param pageIndexInner 目标页索引（包含前面空白区域的item数量）
     */
    private void setCurrentPageInner(int pageIndexInner) {

        if (pageIndexInner < mBlankItemNum) {
            pageIndexInner = mBlankItemNum;
        } else if (pageIndexInner >= (getAdapter().getItemCount() - mBlankItemNum)) {
            pageIndexInner = getAdapter().getItemCount() - mBlankItemNum - 1;
        }

        smoothScrollTo(getItemX(pageIndexInner));
        //如果初始化时滑动到很后的item，会导致getItemViewByPosition返回的view为null，所以加上判断，如果为空，则延迟获取view
        View view = getItemViewByPosition(pageIndexInner);
        if (view != null) {
            changeItemToBigStyle(view);
            if (mCurrentItemListener != null) {
                mCurrentItemListener.onCurrentItemStop(mCurrentViewPos - mBlankItemNum, mCurrentView);
            }
        }else {
            final int finalPageIndexInner = pageIndexInner;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeItemToBigStyle(getItemViewByPosition(finalPageIndexInner));
                    if (mCurrentItemListener != null) {
                        mCurrentItemListener.onCurrentItemStop(mCurrentViewPos - mBlankItemNum, mCurrentView);
                    }
                }
            }, mScrollDuration);
        }

    }

    /**
     * 设置选中页
     *
     * @param pageIndex 目标页索引（不包含前面空白区域的item数量）
     */
    public void setCurrentPage(final int pageIndex) {
        /**
         * 初始化后再调用setCurrentPageInner，否则getItemViewByPosition会异常
         */
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentPageInner(pageIndex + mBlankItemNum);
            }
        });
    }

    /**
     * 移动选中视图到正中间
     */
    private void smoothScrollToCurrentItem() {
        smoothScrollTo(getItemX(mCurrentViewPos));
    }

    /**
     * 滑动列表到指定坐标，使用动画
     *
     * @param x 坐标
     */
    private void smoothScrollTo(final int x) {
        mTargetScrollX = x;
        mAnimator.start();

        /**
         * 注意：
         * 1. recyclerview的scrollTo()是无效的，可以通过源码查看。所以导致了以下动画无效
         *    ObjectAnimator.ofInt(this, "scrollX", x).setDuration(DURATION_SCROLL).start();
         * 2. 所以通过scrollBy来进行滚动，scrollBy(x)中的x，其实就是在当前ScrollX的基础上再进行x移动，
         *    因此只要得到当前的scrollX，然后通过scrollBy(targetX-scrollx)即可达到scrollTo的效果。
         * 3. 但recyclerview的scrollTo的getScrollX一直都是返回0，所以通过getScollXDistance()方法来获取scrollX
         * 4. 单纯的scrollBy是没有动画效果的，所以另外通过ValueAnimator来实现平滑滚动
         * 5. 还有其他解决方案，如LinearLayoutManager的startSmoothScroll来进行滑动，但它要自定义SmoothScroller，还没掌握相关知识。
         */
    }

    public int getScollXDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        if (firstVisiableChildView == null) {
            return mTargetScrollX;
        }
        int itemWidth = firstVisiableChildView.getWidth();
        return (position) * itemWidth - firstVisiableChildView.getLeft();
    }

    public interface CurrentItemListener {
        void onCurrentItemStop(int position, View viewSelected);

        void onCurrentItemChange(int position, View viewSelected);
    }

    public void setCurrentItemListener(CurrentItemListener listener) {
        mCurrentItemListener = listener;
    }
}
