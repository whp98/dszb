package xyz.intellij.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;

import cn.jzvd.Jzvd;
import xyz.intellij.player.MyJZVD.MyJzvdStd;
import xyz.intellij.player.util.entity.RateEntity;
import xyz.intellij.player.util.entity.RateEntityPK;
import xyz.intellij.player.util.entity.Repo;
import xyz.intellij.player.util.entity.StreamEntity;
import xyz.intellij.player.util.entity.UserEntity;

//这个主要是是用饺子视频播放器来实现播放
public class JVActivity extends AppCompatActivity {
    MyJzvdStd jzvdStd;
    private StreamEntity streamEntity;
    private UserEntity userEntity;
    private RateEntity rateEntity11;
    private RatingBar qrate;
    private RatingBar crate;
    private RateEntityPK a;
    private TextView textRate;
    private TextView textDiscribe;
    String TAG = "player_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jv);
        Intent intent = this.getIntent();
        userEntity = (UserEntity) intent.getSerializableExtra("user");
        streamEntity = (StreamEntity)intent.getSerializableExtra("stream");

        rateEntity11 = new RateEntity();
        rateEntity11.setUserId(userEntity.getUserId());
        rateEntity11.setStreamId(streamEntity.getStreamId());
        if (streamEntity!=null){
            setTitle(streamEntity.getStreamName());
        }

        //描述和评分
        textRate = findViewById(R.id.textRate);
        textDiscribe = findViewById(R.id.textDescribe);
        textRate.setText("质量评分："+streamEntity.getStreamQrate()+"内容评分："+streamEntity.getStreamCrate());
        textDiscribe.setText(streamEntity.getStreamInterduce());
        //获取评分
        qrate = findViewById(R.id.ratingBarQ);
        crate = findViewById(R.id.ratingBarC);
        qrate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser){
                    rateEntity11.setqRate((double)rating);
                }
            }
        });
        crate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser){
                    rateEntity11.setcRate((double)rating);
                }
            }
        });
        a=new RateEntityPK();
        a.setStreamId(streamEntity.getStreamId());
        a.setUserId(userEntity.getUserId());
        new JVActivity.OnBackground1().execute(a);
        //播放器内容
        jzvdStd = (MyJzvdStd) findViewById(R.id.jz_video);
        jzvdStd.setUp(streamEntity.getStreamUrl(), streamEntity.getStreamName());
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop()...");
        new OnBackground().execute(rateEntity11);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()...");
        Jzvd.releaseAllVideos();
    }
    //设置评分的异步内部类
    class OnBackground1 extends AsyncTask<RateEntityPK,Void, RateEntity> {
        @Override
        protected RateEntity doInBackground(RateEntityPK... rateEntityPKS) {
            Repo repo = new Repo();
            try {
                return repo.getrate(rateEntityPKS[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(RateEntity rateEntity) {
            super.onPostExecute(rateEntity);
            if (rateEntity.getcRate()!=null){
                BigDecimal number = new BigDecimal(""+rateEntity.getcRate());
                crate.setRating(number.floatValue());
                rateEntity11.setcRate(number.doubleValue());
            }
            if (rateEntity.getqRate()!=null){
                BigDecimal number1 = new BigDecimal(""+rateEntity.getqRate());
                qrate.setRating(number1.floatValue());
                rateEntity11.setqRate(number1.doubleValue());
            }
        }
    }
    //内部类用于异步存储用户评分
    class OnBackground extends AsyncTask<RateEntity,Void,RateEntity>{

        @Override
        protected RateEntity doInBackground(RateEntity... rateEntities) {
            Repo tmp = new Repo();
            try {
                return tmp.saverate(rateEntities[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(RateEntity rateEntity) {
            super.onPostExecute(rateEntity);
        }
    }
}
