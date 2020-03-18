package com.example.sample.principle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sample.R;
import com.example.sample.activity.BaseActivity;
import com.example.sample.adapter.RecyclerViewTestAdapter;
import com.example.sample.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class PrincipleMainActivity extends BaseActivity {
    private long startTime;

    @BindView(R.id.test_img)
    ImageView mTestImg;
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577705790000&di=418b247a50d5d8477aee62404e942181&imgtype=0&src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2015%2F0408%2F779334da99e40adb587d0ba715eca102.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princelpe_main);
        ButterKnife.bind(this);
        Glide.with(this).load(url).into(mTestImg);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewTestAdapter adapter = new RecyclerViewTestAdapter(this,fruitList);
        recyclerView.setAdapter(adapter);
    }

    private List<String> fruitList = new ArrayList<>();
    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", 0);
            fruitList.add("Apple");
            Fruit banana = new Fruit("Banana", 0);
            fruitList.add("Banana");
            Fruit orange = new Fruit("Orange", 0);
            fruitList.add("Orange");


        }
    }


    @Override
    public void onBackPressed() {

        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            ToastUtil.showToast(PrincipleMainActivity.this, "再按一次退出");
            startTime = currentTime;
        } else {
            moveTaskToBack(true);
        }
    }

    private Timer timer ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(PrincipleMainActivity.this, PrincipleActivity.class);
                    startActivity(intent);
            }
        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                },2000);
                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                timer.cancel();
                timer = null;
                break;
        }

        return super.onTouchEvent(event);
    }

    public class Fruit {

        private String name;
        private int imageId;

        public Fruit(String name, int imageId){
            this.name = name;
            this.imageId = imageId;

        }

        public String getName() {
            return name;
        }

        public int getImageId() {
            return imageId;
        }
    }

}
