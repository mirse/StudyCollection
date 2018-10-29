package com.example.dezhiwang.studycollection.Activity;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dezhiwang.studycollection.MyView.LetterIndex;
import com.example.dezhiwang.studycollection.R;
import com.example.dezhiwang.studycollection.letterindex.LetterIndexAdapter;
import com.example.dezhiwang.studycollection.letterindex.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.MissingResourceException;

public class LetterIndexActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    private LinearLayoutManager linearmanger;
    private LetterIndexAdapter letterIndexAdapter;
    private ArrayList<Person> persons;
    private RecyclerView mRv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_index);
        initData();
        final LetterIndex mLetterIndex = findViewById(R.id.letterindex);
        final TextView mTv = findViewById(R.id.tv);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(linearmanger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(letterIndexAdapter = new LetterIndexAdapter(this, persons));
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                String substring = persons.get(firstVisibleItemPosition).getPingyin().substring(0, 1);
                mLetterIndex.setWords(substring);
            }
        });

        mLetterIndex.setIndexListener(new LetterIndex.IndexListener() {
            @Override
            public void setIndexListener(String word) {
                getWord(word);
                mTv.setVisibility(View.VISIBLE);
                mTv.setText(word);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setVisibility(View.GONE);
                    }
                },1000);
            }
        });
    }

    private void getWord(String word) {
        for (int i=0;i<persons.size();i++){
            String substring = persons.get(i).getPingyin().substring(0, 1);
            if (substring.equals(word)&&persons.size()>=i)
            {
               // View childAt = mRv.getChildAt(i);
                moveToPosition(linearmanger,mRv,i);
                break;
            }
        }
    }

    private void moveToPosition(LinearLayoutManager linearmanger, RecyclerView mRv, int i) {
        int first = linearmanger.findFirstVisibleItemPosition();
        int last = linearmanger.findLastVisibleItemPosition();
    //    Log.i("text","first:"+first+" last:"+last+" i="+i);
        if (i<first){
            mRv.scrollToPosition(i);
        }
        else if (i<=last){
            int top = mRv.getChildAt(i - first).getTop();
            mRv.scrollBy(0, top);
            //mRv.scrollToPosition(i);
        }else{
            mRv.scrollToPosition(i);
        }

    }

    private void initData() {
        persons=new ArrayList<>();
        persons.add(new Person("张三"));
        persons.add(new Person("李四"));
        persons.add(new Person("王五"));
        persons.add(new Person("张思"));
        persons.add(new Person("欧阳"));
        persons.add(new Person("欧克"));
        persons.add(new Person("刘备"));
        persons.add(new Person("张飞"));
        persons.add(new Person("关羽"));

        persons.add(new Person("诸葛亮"));
        persons.add(new Person("夏侯霸"));
        persons.add(new Person("孙尚香"));
        persons.add(new Person("刘禅"));

        persons.add(new Person("左慈"));
        persons.add(new Person("黄月英"));
        persons.add(new Person("甘夫人"));
        persons.add(new Person("简雍"));
        persons.add(new Person("糜烂"));

        persons.add(new Person("陈芳"));
        persons.add(new Person("陈庆之"));
        persons.add(new Person("白起"));
        persons.add(new Person("吴起"));
        persons.add(new Person("庞统"));
        persons.add(new Person("姜维"));
        persons.add(new Person("赵云"));

        persons.add(new Person("黄忠"));
        persons.add(new Person("魏延"));
        persons.add(new Person("马岱"));
        persons.add(new Person("马超"));
        persons.add(new Person("孙权"));

        persons.add(new Person("步练师"));
        persons.add(new Person("孙策"));
        persons.add(new Person("孙坚"));
        persons.add(new Person("周瑜"));

        persons.add(new Person("黄盖"));
        persons.add(new Person("程普"));


        persons.add(new Person("陆逊"));
        persons.add(new Person("吴国太"));
        persons.add(new Person("甘宁"));
        persons.add(new Person("吕蒙"));

        persons.add(new Person("大乔"));
        persons.add(new Person("小乔"));
        persons.add(new Person("二张"));
        persons.add(new Person("鲁肃"));

        persons.add(new Person("丁奉"));
        persons.add(new Person("周泰"));
        persons.add(new Person("曹操"));
        persons.add(new Person("郭嘉"));
        persons.add(new Person("荀彧"));

        persons.add(new Person("曹丕"));
        persons.add(new Person("曹植"));
        persons.add(new Person("曹冲"));
        persons.add(new Person("曹仁"));
        persons.add(new Person("曹爽"));
        persons.add(new Person("曹真"));

        persons.add(new Person("张辽"));
        persons.add(new Person("程昱"));
        persons.add(new Person("李典"));
        persons.add(new Person("甄姬"));
        persons.add(new Person("邓艾"));

        persons.add(new Person("钟会"));
        persons.add(new Person("张合"));
        persons.add(new Person("典韦"));
        persons.add(new Person("吕布"));

        persons.add(new Person("貂蝉"));
        persons.add(new Person("教主"));


        persons.add(new Person("大嘴"));
        persons.add(new Person("甲鱼"));
        persons.add(new Person("双雄"));
        persons.add(new Person("马腾"));

        persons.add(new Person("孔融"));
        persons.add(new Person("坨子"));


        persons.add(new Person("庞德"));
        persons.add(new Person("法正"));
        persons.add(new Person("张松"));
        persons.add(new Person("夏侯渊"));
        persons.add(new Person("夏侯敦"));
        persons.add(new Person("司马懿"));
        persons.add(new Person("张春华"));

        persons.add(new Person("满宠"));
        persons.add(new Person("太史慈"));
        persons.add(new Person("凌统"));
        persons.add(new Person("袁术"));

        persons.add(new Person("公孙瓒"));
        persons.add(new Person("刘协"));
        persons.add(new Person("祝融"));

        persons.add(new Person("孟获"));


        persons.add(new Person("A情债"));
        persons.add(new Person("A一起走过的日子"));
        persons.add(new Person("A真永远"));
        persons.add(new Person("A夜太黑"));

        persons.add(new Person("A挥着翅膀的女孩"));
        persons.add(new Person("A红豆"));
        persons.add(new Person("A孤星泪"));
        persons.add(new Person("A练习"));


        persons.add(new Person("A第一次"));
        persons.add(new Person("A缠绵"));
        persons.add(new Person("A给自己的情书"));
        persons.add(new Person("A樵心遇郎君，妾心连衣裳。"));

        persons.add(new Person("A你们男人造的孽，非要说什么红颜祸水"));
        persons.add(new Person("A缠绵"));
        persons.add(new Person("A给自己的情书"));
        persons.add(new Person("A樵心遇郎君，妾心连衣裳。"));

        persons.add(new Person("A好一筹将计就计。"));
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPingyin().compareTo(o2.getPingyin());
            }
        });
    }
}
