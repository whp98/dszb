package xyz.intellij.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import xyz.intellij.player.util.entity.Repo;
import xyz.intellij.player.util.entity.UserEntity;

//登录的逻辑
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText pwd;
    private Button login;
    private TextView news_show;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("登录");
        email = findViewById(R.id.account_input);
        pwd =findViewById(R.id.password_input);
        login = findViewById(R.id.btn_login);
        news_show = findViewById(R.id.news_show);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        UserEntity s = new UserEntity();
                        s.setUserEmail(email.getText().toString());
                        s.setUserPwd(pwd.getText().toString());
                        Repo a = new Repo();
                        try {
                            s = a.login(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (s==null){
//                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
//                            setTitle("登录失败");
                            news_show.setText("登录失败");

                        }else {
//                            Toast.makeText(LoginActivity.this, "登录成功欢迎"+s.getUserNikename(), Toast.LENGTH_LONG).show();
                            news_show.setText("登录成功");
                            Intent intent = new Intent(LoginActivity.this, StreamListActivity.class);
                            Bundle data = new Bundle();
                            data.putSerializable("user", s);
                            intent.putExtras(data);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).start();
                break;

            default:
                break;
        }
    }
}
