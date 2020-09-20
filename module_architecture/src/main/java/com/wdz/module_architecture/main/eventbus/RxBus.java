package com.wdz.module_architecture.main.eventbus;



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

public class RxBus {
    private static volatile RxBus rxBus;
    private final Subject<Object> mSubject;
    private HashMap<String, CompositeDisposable> mHashMap;

    private RxBus(){
        //转换成一个线程安全的Subject对象
        mSubject = PublishSubject.create().toSerialized();
    }
    public static RxBus getInstance(){
        if (rxBus == null){
            synchronized (RxBus.class){
                if (rxBus == null){
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }
    public void post(Object object){
        mSubject.onNext(object);
    }
    /**
     * 返回指定类型的带背压的Flowable实例
     *
     * @param <T>
     * @param type
     * @return
     */
    public <T>Flowable<T> getObservable(Class<T> type){
        return mSubject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }

    /**
     * 默认的订阅方法
     * @param type
     * @param next
     * @param error
     * @param <T>
     * @return
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);

    }
    public boolean hasObservers(){
        return mSubject.hasObservers();
    }

    /**
     * 保存已有的观察者订阅
     * @param o
     * @param disposable
     */
    public void addSubscription(Object o,Disposable disposable){
        if (mHashMap == null){
            mHashMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mHashMap.get(key)!=null){
            mHashMap.get(key).add(disposable);
        }
        else{
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mHashMap.put(key,compositeDisposable);
        }
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscription(Object o){
        if (mHashMap == null){
            return;
        }
        String key = o.getClass().getName();
        if (!mHashMap.containsKey(key)){
            return;
        }
        if (mHashMap.get(key)!=null){
            mHashMap.get(key).dispose();
        }
        mHashMap.remove(key);
    }

}
