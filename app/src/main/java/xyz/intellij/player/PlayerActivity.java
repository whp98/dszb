package xyz.intellij.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.math.BigDecimal;

import xyz.intellij.player.util.entity.RateEntity;
import xyz.intellij.player.util.entity.RateEntityPK;
import xyz.intellij.player.util.entity.Repo;
import xyz.intellij.player.util.entity.StreamEntity;
import xyz.intellij.player.util.entity.UserEntity;
//播放器的逻辑

public class PlayerActivity extends AppCompatActivity {
    //private PlayerView playerView;
    private static final String TAG = "PlayerActivity";
    private PlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private TextView textRate;
    private TextView textDiscribe;
    private StreamEntity streamEntity;
    private UserEntity userEntity;
    private RatingBar qrate;
    private RatingBar crate;
    private RateEntityPK a;
    private RateEntity rateEntity11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
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
        new OnBackground1().execute(a);
        //播放器
        simpleExoPlayerView = findViewById(R.id.player_view);
        Uri mp4VideoUri =Uri.parse(streamEntity.getStreamUrl());
        Log.e("AAA",streamEntity.getStreamUrl());
        streamEntity.getStreamInterduce();
        // 创建播放器
        player = ExoPlayerFactory.newSimpleInstance(this);
        int h = simpleExoPlayerView.getResources().getConfiguration().screenHeightDp;
        int w = simpleExoPlayerView.getResources().getConfiguration().screenWidthDp;
        //播放控制设置
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();
        // 将视图绑定
        simpleExoPlayerView.setPlayer(player);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "playerdemo"));
        //hls
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .setAllowChunklessPreparation(true)
                .createMediaSource(mp4VideoUri);
        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        // 准备播放源
        player.prepare(videoSource);
        //监听器
        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged... ");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState+"|||isDrawingCacheEnabled():"+simpleExoPlayerView.isDrawingCacheEnabled());
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.setPlayWhenReady(true); //准备好就播放
    }

//-------------------------------------------------------安卓生命周期---------------------------------------------------------------------------------------------

    //设置评分的异步内部类
    class OnBackground1 extends AsyncTask<RateEntityPK,Void, RateEntity>{
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
        player.release();
    }
}
