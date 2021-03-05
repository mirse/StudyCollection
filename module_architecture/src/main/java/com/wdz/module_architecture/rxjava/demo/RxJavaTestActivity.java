package com.wdz.module_architecture.rxjava.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

@Route(path = ARouterConstant.ACTIVITY_RXJAVA)
public class RxJavaTestActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Disposable disposable;
    @BindView(R2.id.tv_policy)
    TextView tv_policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        ButterKnife.bind(this);
//        Spanned spanned = Html.fromHtml("阅读"+"<a href=\"http://blog.csdn.net/CAIYUNFREEDOM\">policy</a>");
//        tv_policy.setText(spanned);
//        tv_policy.setMovementMethod(LinkMovementMethod.getInstance());


        String registerPrivacyHeader = "Review Google’s ";
        String registerPrivacyEnd = "Privacy Policy.";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(registerPrivacyHeader);
        stringBuilder.append(registerPrivacyEnd);

        stringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                ARouter.getInstance().build(RouterConstance.ACTIVITY_URL_PRIVACY)
//                        .withString(PrivacyActivity.KEY_URL,getString(R.string.privacy_url))
//                        .navigation();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, registerPrivacyHeader.length(), registerPrivacyHeader.length() + registerPrivacyEnd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getColor(R.color.login_no_account_text_color_2));
        ForegroundColorSpan colorSpanHeader = new ForegroundColorSpan(getColor(R.color.login_no_account_text_color_1));
        stringBuilder.setSpan(colorSpan, registerPrivacyHeader.length(), registerPrivacyHeader.length() + registerPrivacyEnd.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        stringBuilder.setSpan(colorSpanHeader, 0, registerPrivacyHeader.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        stringBuilder.setSpan(new UnderlineSpan(), registerPrivacyHeader.length(), registerPrivacyHeader.length() + registerPrivacyEnd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_policy.setText(stringBuilder);
        //不设置 没有点击事件
        tv_policy.setMovementMethod(LinkMovementMethod.getInstance());
        //设置点击后的颜色为透明
        tv_policy.setHighlightColor(Color.TRANSPARENT);


//        Observable.interval(2,1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i(TAG, "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.i(TAG, "onNext: "+aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });





//        Observable.create(new ObservableOnSubscribe<Integer>() {
//
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//            }
//        }).concatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                List<String> list = new ArrayList<>();
//                for (int i = 0; i < 3; i++) {
//                    list.add(i+"个");
//                }
//                return Observable.fromIterable(list);
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//                Log.i(TAG, "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(String s) {
//                if (s.equals("1")){
//                    disposable.dispose();
//                }
//                Log.i(TAG, "onNext: "+s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete: ");
//            }
//        });


//        Observable.just(1,2,3,4,5)
//                .buffer(3,1)
//                .subscribe(new Observer<List<Integer>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Integer> list) {
//                        Log.i(TAG, "onNext: "+list.size());
//                        for (Integer integer:list) {
//                            Log.i(TAG, "onNext: 事件："+integer);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "被观察者1发送了事件1");
//                emitter.onNext(1);
//                // 为了方便展示效果，所以在发送事件后加入2s的延迟
//                Thread.sleep(1000);
//
//                Log.d(TAG, "被观察者1发送了事件2");
//                emitter.onNext(2);
//                Thread.sleep(1000);
//
//                Log.d(TAG, "被观察者1发送了事件3");
//                emitter.onNext(3);
//                Thread.sleep(1000);
//
//                emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//
//
//        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.d(TAG, "被观察者2发送了事件A");
//                emitter.onNext("A");
//                Thread.sleep(1000);
//
//                Log.d(TAG, "被观察者2发送了事件B");
//                emitter.onNext("B");
//                Thread.sleep(1000);
//
//                Log.d(TAG, "被观察者2发送了事件C");
//                emitter.onNext("C");
//                Thread.sleep(1000);
//
//                Log.d(TAG, "被观察者2发送了事件D");
//                emitter.onNext("D");
//                Thread.sleep(1000);
//
//                //emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.newThread());
//
//        Observable.zip(observable, stringObservable, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer+s;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, "onNext: "+s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete: ");
//            }
//        });

//        Observable.combineLatest(
//                Observable.just(1, 2, 3),
//                Observable.just(1, 2, 3),
//                Observable.just(1, 2, 3)
//                , new Function3<Integer, Integer, Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
//                        return integer+integer2+integer3;
//                    }
//                }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.i(TAG, "accept: "+integer);
//            }
//        });


        getMsg().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });

    }


    private Observable<String> getMsg(){
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                testGetMsg(emitter);
                emitter.onComplete();
            }
        });
        return stringObservable.timeout(3*1000, TimeUnit.MILLISECONDS).retry(3);

    }



    int count;
    private void testGetMsg(final ObservableEmitter<String> emitter) throws Exception {
        count++;
        Log.i(TAG, "testGetMsg: send");
        getResult(count, new OnGetResultListener() {
            @Override
            public void getSuccess() {
                if (!emitter.isDisposed()){
                    emitter.onNext("send");
                }
            }

            @Override
            public void getFail() {
                if (!emitter.isDisposed()){
                    emitter.onError(new Throwable(""));
                }

            }
        });
    };

    private void getResult(final int count, final OnGetResultListener onGetResultListener) throws InterruptedException {
        Log.i(TAG, "getResult: "+count);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count == 5){
                    onGetResultListener.getSuccess();
                }
                else{
                    onGetResultListener.getFail();
                }
            }
        },6000);

    }
    private interface OnGetResultListener{
        void getSuccess();
        void getFail();
    }

}
