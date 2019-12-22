package xyz.intellij.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.stream.Stream;

import xyz.intellij.player.util.entity.Repo;
import xyz.intellij.player.util.entity.StreamEntity;
import xyz.intellij.player.util.entity.UserEntity;

public class NewStreamActivity extends AppCompatActivity {

    private EditText streamname;
    private EditText streamuri;
    private EditText streaminterduce;
    private Button button;
    private UserEntity userEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stream);
        setTitle(getString(R.string.newstream));
        streamname = findViewById(R.id.newstream_name);
        streamuri = findViewById(R.id.newstream_uri);
        streaminterduce = findViewById(R.id.newstream_interduce);
        button = findViewById(R.id.newstream_btn);
        Intent intent = this.getIntent();
        userEntity = (UserEntity) intent.getSerializableExtra("user");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.newstream_btn){
                    StreamEntity streamEntity = new StreamEntity();
                    streamEntity.setStreamName(streamname.getText().toString());
                    streamEntity.setStreamUrl(streamuri.getText().toString());
                    streamEntity.setStreamInterduce(streaminterduce.getText().toString());
                    new Onbackground().execute(streamEntity);
                }
            }
        });
    }
    class Onbackground extends AsyncTask<StreamEntity,Void,String>{

        @Override
        protected String doInBackground(StreamEntity... streamEntities) {
            Repo repo = new Repo();
            try {
                StreamEntity s = repo.streamadd(streamEntities[0]);
                if(s==null){
                    return "失败";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "成功";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("成功")){
                Toast.makeText(getApplicationContext(), s,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewStreamActivity.this,PlayerActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user",userEntity);
                intent.putExtras(data);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), s,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
