package com.wdz.common;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @description:
 * @author: dezhi.wang
 * @date :   2021/5/31 15:15
 */
public class RxBus {
    private static final RxBus RX_BUS = new RxBus();

    private final Subject<Object> rxBus;

    public RxBus() {
        rxBus = PublishSubject.create().toSerialized();
    }
    public static RxBus getInstance(){
        return RX_BUS;
    }
    public void post(Object object){
        rxBus.onNext(object);
    }
    public <T> Observable<T> toObservable(Class<T> tClass){
        return rxBus.ofType(tClass);
    }
}
