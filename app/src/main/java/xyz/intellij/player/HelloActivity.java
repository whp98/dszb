package xyz.intellij.player;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelloActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    private Button reg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        login=findViewById(R.id.hello_btn_login);
        reg=findViewById(R.id.hello_btn_reg);
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hello_btn_login:
                Intent intent = new Intent(HelloActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case  R.id.hello_btn_reg:
                Intent intent1 = new Intent(HelloActivity.this,EnrollActivity.class);
                startActivity(intent1);
                finish();
                break;
                default:break;
        }
    }
}
