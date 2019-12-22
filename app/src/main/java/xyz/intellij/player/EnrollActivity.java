package xyz.intellij.player;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import xyz.intellij.player.util.entity.Repo;
import xyz.intellij.player.util.entity.UserEntity;

//注册的逻辑
public class EnrollActivity extends AppCompatActivity {

    private EditText textNikename;
    private EditText textEmail;
    private EditText textPwd;
    private Button button;
    private TextView show;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle(getString(R.string.reg_title));
        textNikename = findViewById(R.id.reg_nikename_input);
        textEmail = findViewById(R.id.reg_account_input);
        textPwd = findViewById(R.id.reg_password_input);
        button = findViewById(R.id.btn_enroll);
        show = findViewById(R.id.reg_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.btn_enroll){
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserNikename(textNikename.getText().toString());
                    userEntity.setUserEmail(textEmail.getText().toString());
                    userEntity.setUserPwd(textPwd.getText().toString());
                    new BackGround().execute(userEntity);
                }
            }
        });
    }
    class BackGround extends AsyncTask<UserEntity,Void,String>{

        @Override
        protected String doInBackground(UserEntity... userEntities) {
            Repo repo = new Repo();
            try {
                UserEntity sss;
                sss = repo.register(userEntities[0]);
                if(sss != null){
                    return "注册成功";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "注册失败，邮箱已经被使用";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            show.setText(s);
            if (s.equals("注册成功")){
                Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }
}
