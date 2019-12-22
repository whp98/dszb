package xyz.intellij.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import xyz.intellij.player.Adapter.StreamAdapter;
import xyz.intellij.player.util.entity.Repo;
import xyz.intellij.player.util.entity.StreamEntity;
import xyz.intellij.player.util.entity.UserEntity;

public class StreamListActivity extends AppCompatActivity {

    private List<StreamEntity> streamEntities;
    private ListView listView;
    private boolean isload;
    private ProgressBar netPicProgressBar;
    private UserEntity userEntity;
    private Button newstream;
    //展示列表
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_list);
        listView = findViewById(R.id.listview);
        netPicProgressBar = findViewById(R.id.progressBar);
        newstream = findViewById(R.id.btn_newstream);
        Intent intent = this.getIntent();
        userEntity = (UserEntity) intent.getSerializableExtra("user");
        setTitle("视频列表:欢迎"+userEntity.getUserNikename());
        newstream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.btn_newstream){
                    Intent intent1 = new Intent(StreamListActivity.this,NewStreamActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("user",userEntity);
                    intent1.putExtras(data);
                    startActivity(intent1);
                }
            }
        });
        new BackGround().execute();
    }
    //内部类用于异步加载评分
    class BackGround extends AsyncTask<Void,Void,List<StreamEntity>>{

        @Override
        protected List<StreamEntity> doInBackground(Void... voids) {
            Repo rep = new Repo();
            List<StreamEntity> streamEntities1=null;
            try {
                streamEntities1 = rep.getall();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return streamEntities1;
        }

        @Override
        protected void onPostExecute(final List<StreamEntity> streamEntities) {
            super.onPostExecute(streamEntities);
            netPicProgressBar.setVisibility(View.GONE);
            StreamAdapter streamAdapter = new StreamAdapter(StreamListActivity.this,R.layout.list_item,streamEntities);
            listView.setAdapter(streamAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(StreamListActivity.this, JVActivity.class);
                        Bundle data = new Bundle();
                    data.putSerializable("stream", streamEntities.get(i));
                    data.putSerializable("user",userEntity);
                    intent.putExtras(data);
                        startActivity(intent);
                }
            });
        }
    }


}
