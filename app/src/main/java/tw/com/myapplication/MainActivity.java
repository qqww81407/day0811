package tw.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userid, userpwd;
    private Button btnok,deldata,cleandata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        checkUserinfo();


    }

    private void checkUserinfo(){
        //讀取資料
        SharedPreferences txt = getSharedPreferences("LccUser",MODE_PRIVATE);
        String user = txt.getString("UserID","");
        String pwd = txt.getString("UserPWD","");
        //userid.setText(user);
        //userpwd.setText(pwd);

        if (user.equals("lcc")&& pwd.equals("good")){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        }


    }

    private void findViews(){
        userid = findViewById(R.id.userid);
        userpwd = findViewById(R.id.userpwd);
        btnok = findViewById(R.id.btnok);
        deldata = findViewById(R.id.deldata);
        cleandata = findViewById(R.id.cleandata);

        deldata.setOnClickListener(v -> {
            //刪除某個資料
            SharedPreferences sp = getSharedPreferences("LccUser",MODE_PRIVATE);
            sp.edit().remove("UserPWD").commit();
        });

        cleandata.setOnClickListener(v -> {
            //清除資料
            SharedPreferences sp = getSharedPreferences("LccUser",MODE_PRIVATE);
            sp.edit().clear().commit();

        });



        btnok.setOnClickListener(v -> {
            String user = userid.getText().toString();
            String pwd = userpwd.getText().toString();

            if (user.equals("lcc")&& pwd.equals("good")){
                //儲存資料 -> sharePreferences
                SharedPreferences userinfo = getSharedPreferences("LccUser",MODE_PRIVATE);
                userinfo.edit()
                        .putString("UserID",user)
                        .putString("UserPWD",pwd)
                        .commit();

                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), "帳密錯誤", Toast.LENGTH_SHORT).show();
                userid.setText("");
                userpwd.setText("");
            }

        });


    }
}