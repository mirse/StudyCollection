package com.wdz.studycollection.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.TimeUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.wdz.studycollection.R;
import com.wdz.studycollection.eventbus.MessageEvent;
import com.wdz.studycollection.eventbus.RxBus;
import com.wdz.studycollection.rxjava.bean.Translation;
import com.wdz.studycollection.rxjava.bean.TranslationEnToCh;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RxDemoActivity extends AppCompatActivity {
    private static final String TAG = "RxDemoActivity";
    private Disposable mDisposable;
    @SuppressLint("CheckResult")

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.list)
    Button mBtnList;
    @BindView(R.id.ed)
    EditText mEd;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.bt_start)
    Button mBtnStart;

    private int maxCount = 10;
    private int currnetCount = 0;
    private int waitRetryTime = 0;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_demo);
        ButterKnife.bind(this);
        //rxjava 链式表达-------------------------------------------------------------------
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            //创建被观察者&生产时间
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//        }).subscribe(new Observer<Integer>() {
//            //1、subscribe 连接观察者与被观察者  2、创建观察者 & 响应事件
//            @Override
//            public void onSubscribe(Disposable d) {
//                mDisposable = d;
//                Log.d(TAG, "开始采用subscribe连接");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                if (integer == 2){
//                    mDisposable.dispose();
//                }
//                Log.d(TAG, "对Next事件"+ integer +"作出响应");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "对Error事件作出响应"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "对Complete事件作出响应");
//            }
//        });

            //创建操作符   doOnNext观察者被通知前  doOnSubscribe 事件被订阅之前(事件源被发起之前)-------------------------------------------------------------------
//        Observable.interval(2,1, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://fy.iciba.com/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .build();
//
//                GetRequestInterface request = retrofit.create(GetRequestInterface.class);
//
//                Observable<Translation> observable = request.getCall();
//                observable.subscribeOn(Schedulers.io()) // 切换到IO线程进行网络请求 (订阅)
//                        .observeOn(AndroidSchedulers.mainThread()) // 切换回到主线程 处理请求结果(观察)
//                        .subscribe(new Observer<Translation>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(Translation translation) {
//                                Log.i(TAG,translation.toString());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.i(TAG,e.toString());
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//
//
//            }
//        }).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.i(TAG,"along"+aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "对Error事件作出响应");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "对Complete事件作出响应");
//            }
//        });

        //变化操作符-------------------------------------------------------------------
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
//        Observable<Translation> observable1 = request.getCall();
//        Observable<Translation> observable2 = request.getCall_2();
//        observable1.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Translation>() {
//                    @Override
//                    public void accept(Translation translation) throws Exception {
//                        Log.i(TAG,"第1次网络请求成功:"+translation.content.out);
//
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
//                    @Override
//                    public ObservableSource<Translation> apply(Translation translation) throws Exception {
//                        return observable2;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Translation>() {
//                    @Override
//                    public void accept(Translation translation) throws Exception {
//                        Log.d(TAG, "第2次网络请求成功:" + translation.content.out);
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.d(TAG,"登录失败");
//                    }
//                });

        //  组合/合并操作符 -------------------------------------------------------------------
        //merge()
//        String  result = "数据源来自 = ";
//        Observable<String> network = Observable.just("网络");
//        Observable<String> file = Observable.just("本地文件");
//        Observable.merge(network,file)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.d(TAG, "数据源有： "+ s  );
//                       // result += s + "+";
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "获取数据完成");
//                        Log.d(TAG,  result  );
//                    }
//                });

        //zip
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
//        Observable<Translation> observable1 = request.getCall().subscribeOn(Schedulers.io());
//        Observable<Translation> observable2 = request.getCall_2().subscribeOn(Schedulers.io());
//        Observable.zip(observable1, observable2, new BiFunction<Translation, Translation, String>() {
//            @Override
//            public String apply(Translation translation, Translation translation2) throws Exception {
//                return translation.toString() + translation2.toString();
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    // 成功返回数据时调用
//                    @Override
//                    public void accept(String combine_infro) throws Exception {
//                        // 结合显示2个网络请求的数据结果
//                        Log.d(TAG, "最终接收到的数据是：" + combine_infro);
//                    }
//                }, new Consumer<Throwable>() {
//                    // 网络请求错误时调用
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.out.println("登录失败");
//                    }
//                });

        //concat firstElement
//        String memoryCache = null;
//        String diskCache = "磁盘缓存";
//        //检查内存
//        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                if (memoryCache!=null){
//                    emitter.onNext(memoryCache);
//                }else{
//                    emitter.onComplete();
//                }
//            }
//        });
//        //检查磁盘
//        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                if (diskCache!=null){
//                    emitter.onNext(diskCache);
//                }else{
//                    emitter.onComplete();
//                }
//            }
//        });
//
//        Observable<String> network = Observable.just("从网络获取");
//
//        Observable.concat(memory,disk,network)
//                .firstElement()
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.d(TAG,"最终获取的来源:"+s);
//                    }
//                });

        //combineLatest()

//        Observable<CharSequence> nameObservable = RxTextView.textChanges(this.name).skip(1);
//        Observable<CharSequence> ageObservable = RxTextView.textChanges(this.age).skip(1);
//        Observable<CharSequence> jobObservable = RxTextView.textChanges(this.job).skip(1);
//
//        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
//            @Override
//            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
//                // 1. 姓名信息
//                //boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) ;
//                // 除了设置为空，也可设置长度限制
//                 boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) && (name.getText().toString().length() > 2 && name.getText().toString().length() < 9);
//
//                // 2. 年龄信息
//                boolean isUserAgeValid = !TextUtils.isEmpty(age.getText());
//                // 3. 职业信息
//                boolean isUserJobValid = !TextUtils.isEmpty(job.getText()) ;
//
//                return isUserNameValid&&isUserAgeValid&&isUserJobValid;
//            }
//        }).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                Log.e(TAG, "提交按钮是否可点击： "+aBoolean);
//                mBtnList.setEnabled(aBoolean);
//            }
//        });

        //重试 retryWhen
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
//        Observable<Translation> observable1 = request.getCall();
//        observable1.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
//                        Log.d(TAG, "发生异常:" + throwable.toString());
//                        if (throwable instanceof IOException) {
//                            Log.d(TAG, "属于IO异常，需重试");
//                            if (currnetCount < maxCount) {
//                                currnetCount++;
//                                Log.d(TAG, "重试次数" + currnetCount);
//                                waitRetryTime = 1000 + currnetCount * 1000;
//                                Log.d(TAG, "等待时间 =" + waitRetryTime);
//                                //为重新订阅发送事件的条件
//                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
//                            } else {
//                                return Observable.error(new Throwable("重试次数已经超过限制次数:" + currnetCount));
//                            }
//                        } else {
//                            return Observable.error(new Throwable("不属于io异常"));
//                        }
//                    }
//                });
//            }
//                }).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<Translation>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(Translation translation) {
//                                Log.d(TAG,  "发送成功:"+translation.toString());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.d(TAG,e.toString());
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });

        //repeatWhen

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
//        Observable<Translation> observable = request.getCall();
//        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
//                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Object o) throws Exception {
//                        Log.i(TAG,"i=="+i);
//                        if (i>3){
//                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
//                            return Observable.error(new Throwable("轮询结束"));
//                        }
//                        else{
//                            return Observable.just(1).delay(2000,TimeUnit.MILLISECONDS);
//                        }
//
//                    }
//                });
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Translation>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Translation translation) {
//                        Log.i(TAG,translation.toString());
//                        i++;
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG,e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        //过滤操作符 debounce()

//        RxTextView.textChanges(mEd)
//                .debounce(1, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CharSequence>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(CharSequence charSequence) {
//                        mTv.setText("发送给服务器的字符 = " + charSequence.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mTv.setText(e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        //throttleFirst()



//        RxView.clicks(mBtnStart)
//                .throttleFirst(2,TimeUnit.SECONDS)
//                .subscribe(new Observer<Unit>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Unit unit) {
//                        Log.d(TAG, "发送了网络请求" );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应" + e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//                });
    }
    @OnClick(R.id.bt_start)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_start:
                en2Ch();
                //EventBus.getDefault().post(new MessageEvent("eventbus 回调了"));
                //EventBus.getDefault().postSticky(new MessageEvent("sticky eventbus 回调"));
                RxBus.getInstance().post("I am Rxbus");
                break;
        }
    }

    private void en2Ch(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequestInterfaceRetrofit getRequestInterfaceRetrofit = retrofit.create(GetRequestInterfaceRetrofit.class);
        Call<TranslationEnToCh> call = getRequestInterfaceRetrofit.getCall_2("I love you");
        call.enqueue(new Callback<TranslationEnToCh>() {
            @Override
            public void onResponse(Call<TranslationEnToCh> call, Response<TranslationEnToCh> response) {
                Log.i(TAG,"response:"+response.body().toString());
            }

            @Override
            public void onFailure(Call<TranslationEnToCh> call, Throwable t) {
                Log.i(TAG,"failed");
            }
        });
    }
    /**
     * eng -> chinese
     */
    private void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequestInterfaceRetrofit getRequestInterfaceRetrofit = retrofit.create(GetRequestInterfaceRetrofit.class);
        Call<Translation> call = getRequestInterfaceRetrofit.getCall();
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                Log.i(TAG,"response:"+response.body().toString());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.i(TAG,"failed");
            }
        });
    }
}
