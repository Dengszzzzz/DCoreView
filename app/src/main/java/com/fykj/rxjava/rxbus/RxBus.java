package com.fykj.rxjava.rxbus;

import java.util.HashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by dengzh on 2017/9/21 0021.
 * 封装rxBus 使用类似EventBus
 */

public class RxBus {

    /**
     * CompositeDisposable用来存储 Disposable，从而达到一个订阅者对应多个 Disposable 的目的。
     * CompositeDisposable是一个 Disposable 的容器，声称可以达到 O(1) 的增、删的复杂度。
     * 这里的做法目的是使用注册观察之后的 Disposable 的 dispose() 方法来取消订阅。
     * */
    private HashMap<String, CompositeDisposable> mSubscriptionMap;
    private static volatile RxBus mRxBus;
    private final Subject<RxEvents<?>> mSubject;


    //单列模式
    public static RxBus getIntanceBus(){
        if (mRxBus==null){
            synchronized (RxBus.class){
                if(mRxBus==null){
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }

    //toSerialized() 保证 onNext() 等方法的线程安全性。
    private RxBus(){
        mSubject = PublishSubject.<RxEvents<?>>create().toSerialized();
    }



    /**
     * 返回指定类型的带背压的Flowable实例
     *
     * @param <T>
     * @param type
     * @return
     */
    public <T>Flowable<T> getObservable(Class<T> type){
        return mSubject.toFlowable(BackpressureStrategy.BUFFER).ofType(type);
    }

    /**
     * 一个默认的订阅方法
     *
     * @param <T>
     * @param type
     * @param next
     * @param error
     * @return
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }

    public void post(RxEvents<?> o){
        mSubject.onNext(o);
    }

    /**
     * rxBus发送
     * @param code     event code
     * @param content  event content
     */
    public void post(int code,Object content){
        RxEvents<Object> event = new RxEvents<>();
        event.code = code;
        event.content = content;
        post(event);
    }


    /**
     * 是否已有观察者订阅
     *
     * @return
     */
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    /**
     * 保存订阅后的disposable
     * @param o
     * @param disposable
     */
    public void addSubscription(Object o, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(key, disposables);
        }
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }
        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)){
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }
        mSubscriptionMap.remove(key);
    }

}
