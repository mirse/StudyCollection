package com.wdz.studycollection.internet;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ProgressBar;

import com.wdz.studycollection.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class OkHttpDemoActivity extends AppCompatActivity {
    private static final String TAG = "OkHttpDemoActivity";
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSE = 2;
    private static final int TYPE_CANCEL = 3;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;
    private final String downUrl = "http://113.215.8.86/softdown1.hao123.com/hao123-soft-online-bcs/soft/2017_10_24_BaiduMusic-11407102.exe";
    @BindView(R.id.progressBar_download)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_demo);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.btn_get,R.id.btn_post,R.id.bt_startDown})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_get:
                //异步get请求
                getData();
                break;
            case R.id.btn_post:
                //post
//                postData();
                testInterceptor();
                break;
            case R.id.bt_startDown:
                new DownLoadTask(new DownLoadListener() {
                    @Override
                    void onProgress(int progress) {
                        progressBar.setProgress(progress);
                    }

                    @Override
                    void onSuccess() {

                    }

                    @Override
                    void onFailed() {

                    }

                    @Override
                    void onPaused() {

                    }

                    @Override
                    void onCanceled() {

                    }
                }).execute(downUrl);
                break;
        }
    }

    private void getData() {
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        //异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void postData() {
        String url = "https://api.github.com/markdown/raw";
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "hello";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType,requestBody))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, "name:"+headers.name(i) + " value:" + headers.value(i));
                }
                Log.d(TAG,"body:"+response.body().string());
            }
        });
    }

    private void postFile(){
        MediaType parse = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File("test.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(parse, file))
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, "name:"+headers.name(i) + " value:" + headers.value(i));
                }
                Log.d(TAG,"body:"+response.body().string());
            }
        });

    }

    /**
     * post方式提交表单
     */
    private void postFormBody(){
        FormBody formBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();

        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(formBody)
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, "name:"+headers.name(i) + " value:" + headers.value(i));
                }
                Log.d(TAG,"body:"+response.body().string());
            }
        });

    }

    /**
     * 拦截器demo
     */
    private void testInterceptor(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoginingInterceptor())
                .build();
        Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .header("User-Agent", "OkHttp Example")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    Log.d(TAG, "onResponse: " + response.body().string());
                    body.close();
                }
            }
        });
    }


    public class LoginingInterceptor implements Interceptor{

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.nanoTime();
            Log.d(TAG,String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long endTime = System.nanoTime();
            Log.d(TAG,String.format("Received response %s in %.1fms%n%s",response.request().url(), (endTime - startTime) / 1e6d, response.headers()));
            return response;
        }
    }

    // TODO: 2019/12/5 断点下载task
    public class DownLoadTask extends AsyncTask<String,Integer,Integer>{
        DownLoadListener downloadListener;
        private long downloadLength = 0;
        private InputStream inputStream;
        private RandomAccessFile savedFile;
        private File file;

        public DownLoadTask(DownLoadListener downloadListener) {
            this.downloadListener = downloadListener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String downUrl = params[0];
            String fileName = downUrl.substring(downUrl.lastIndexOf("/"));
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(path + fileName);
            if (file.exists()){
                downloadLength = file.length();
            }
            long contentSize = getContentSize(downUrl);
            if (contentSize == 0){
                return TYPE_FAILED;
            }
            if (contentSize == downloadLength){
                return TYPE_SUCCESS;
            }
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentSize)
                    .url(downUrl)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response!=null){
                    inputStream = response.body().byteStream();
                    savedFile = new RandomAccessFile(file, "rw");
                    savedFile.seek(downloadLength);
                    byte[] bytes = new byte[1024];
                    int total = 0;
                    int length;
                    while ((length = inputStream.read(bytes))!=-1){
                        if (isCanceled){
                            return TYPE_CANCEL;
                        }
                        else if (isPaused){
                            return TYPE_PAUSE;
                        }
                        else{
                            total+=length;
                            savedFile.write(bytes,0,length);
                            int progress = (int) ((total+downloadLength)*100/contentSize);
                            publishProgress(progress);
                        }

                    }
                    response.body().close();
                    return TYPE_SUCCESS;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try{
                    if (inputStream!=null){
                        inputStream.close();
                    }
                    if (savedFile!=null){
                        savedFile.close();
                    }
                    if (isCanceled && file!=null){
                        file.delete();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
            return TYPE_FAILED;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            if (progress>lastProgress){
                downloadListener.onProgress(progress);
                lastProgress = progress;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            switch (integer){
                case TYPE_SUCCESS:
                    downloadListener.onSuccess();
                    break;
                case TYPE_FAILED:
                    downloadListener.onFailed();
                    break;
                case TYPE_PAUSE:
                    downloadListener.onPaused();
                    break;
                case TYPE_CANCEL:
                    downloadListener.onCanceled();
                    break;
            }
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }

    /**
     * @param downUrl
     * @return 得到下载内容的完整大小
     */
    private long  getContentSize(String downUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(downUrl).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response!=null && response.isSuccessful()){
                long length = response.body().contentLength();
                response.body().close();
                return length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
