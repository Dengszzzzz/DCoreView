package com.fykj.dcoreview.model.recyclerView;

import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.model.recyclerView.magazineRv.MagazineActivity;
import com.fykj.dcoreview.model.recyclerView.simpleRv.CardRvActivity;
import com.fykj.dcoreview.model.recyclerView.simpleRv.SimpleRvActivity;
import com.fykj.dcoreview.model.viewpager.banPager.BanPagerActivity;
import com.fykj.dcoreview.model.viewpager.tabpager.TabPagerActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class RvListActivity extends BaseListShowActivity{
    @Override
    protected void initUI() {
        tvTitle.setText("recyclerView示例");
    }

    @Override
    protected void initData() {
        addClazzBean("卡片滑动recyclerView",MagazineActivity.class);
        addClazzBean("简单的自定义LayoutManager",SimpleRvActivity.class);
        addClazzBean("菱形卡片自定义LayoutManager",CardRvActivity.class);
        mAdapter.notifyDataSetChanged();
    }



    /**
     * RecyclerView 源码总结
     * 问题:
     * 1.RecyclerView，LayoutManager，Adapter，ViewHolder，ItemDecoration这些和
     *   RecycleView使用息息相关的类到底是什么关系?
     * 2.RecyclerView作为列表，绘制流程到底什么样的?
     * 3.RecyclerView有什么不常用的进阶使用方式，但是却很适合RecyclerView作为很“重”的
     *   组件的优化，像setRecyclerPool用处到底是什么?
     * 4.大家都只要要使用RecyclerView替代ListView和GridView，好用，都在用，但是都没有追究到底这背后的
     *   原因到底是什么，RecyclerView到底比ListView好在哪里，到底该不该替换，性能到底提升多少?
     *
     *
     * 一、绘制流程
     * 1.RecyclerView是将绘制流程交给LayoutManager处理，如果没有设置不会测量子View.
     * 2.绘制流程是区分正向绘制和倒置绘制。
     * 3.绘制是先确认锚点，然后向上绘制，向下绘制，fill()至少会执行两次，如果绘制完还有剩余空间，则会再执行一次fill()方法。
     * 4.LayoutManager获得View是从RecyclerView中的Recycler.next()获得,涉及到RecyclerView的缓存策略，如果
     *   缓存没有拿到，则走我们自己写的onCreateView方法。
     * 5.如果RecyclerView宽高没有写死，onMeasure就会执行完子View的measure和Layout方法，onLayout仅仅是重置
     *   一些参数；如果写死，子View的measure和layout会延后到onLayout中执行。
     *
     *二、缓存
     * 1.RecyclerView内部大体可以分为四级缓存：
     *   mAttachedScrap、mCacheViews、ViewCacheExtension、RecycledViewPool。
     * 2.mAttachedScrap、mCacheViews只是对View的复用，不区分type；
     *   ViewCacheExtension、RecycledViewPool是对ViewHolder的复用，区分type。
     * 3.如果缓存ViewHolder时发现超过了mCachedView的限制，会将最老的ViewHolder（也就是mCachedView缓存队列
     *   的第一个ViewHolder）移到RecycledViewPool中。
     * 4.
     *   mAttachedScrap:不调用bindView，生命周期是onLayout函数周期内，作用是用于屏幕内ItemView快速重用。
     *   mCacheViews：不调用bindView，生命周期与mAdapter一致，当mAdapter被更换时，mCacheViews即被缓存
     *                至mRecyclerPool。默认上限2个，即缓存屏幕外2个ItemView。
     *   mRecyclerPool：调用bindView，与自身生命周期一致，不再被引用时即被释放。默认上限为5，技术上可以实现
     *                 所有RecyclerViewPool共用同一个。
     *
     * */
}
