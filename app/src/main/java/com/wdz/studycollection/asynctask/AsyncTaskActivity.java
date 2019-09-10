package com.wdz.studycollection.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wdz.studycollection.R;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class AsyncTaskActivity extends AppCompatActivity {

    private ProgressBar[] m_progressBar;

    private ArrayList<AsyncTask> m_taskList;

    private int m_prgBarIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        m_progressBar = new ProgressBar[7];
        int[] id = new int[]{R.id.download_prg_bar_1, R.id.download_prg_bar_2, R.id.download_prg_bar_3, R.id.download_prg_bar_4, R.id.download_prg_bar_5, R.id.download_prg_bar_6,R.id.download_prg_bar_7};
        for(int i = 0; i<7; i++) {
            m_progressBar[i] = (ProgressBar) findViewById(id[i]);
        }

        m_taskList = new ArrayList<AsyncTask>();

        Button button1 = (Button) findViewById(R.id.begin_download_btn_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_prgBarIndex > 6) {
                    return;
                }
                DownloadTask task = new DownloadTask(m_progressBar[m_prgBarIndex++], AsyncTaskActivity.this);
                m_taskList.add(task);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "westworld");
            }
        });

        Button button2 = (Button) findViewById(R.id.begin_download_btn_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_prgBarIndex > 5) {
                    return;
                }
                DownloadTask task = new DownloadTask(m_progressBar[m_prgBarIndex++], AsyncTaskActivity.this);
                m_taskList.add(task);
                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "westworld");
            }
        });

        Button button3 = (Button) findViewById(R.id.clear_btn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_prgBarIndex = 0;
                for(int i = 0; i<6; i++) {
                    m_progressBar[i].setProgress(0);
                }
                for(int i = 0; i<m_taskList.size(); i++) {
                    m_taskList.get(i).cancel(true);
                }
                m_taskList.clear();
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private ProgressBar progressBar;
        private Context context;

        public DownloadTask(ProgressBar progressBar, Context context) {
            this.progressBar = progressBar;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            for(int i = 0; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {

                }
                publishProgress(i * 10);
            }
            if(strings[0].equals("westworld")) {
                return "welcome!";
            }
            return "reject request!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }
}
