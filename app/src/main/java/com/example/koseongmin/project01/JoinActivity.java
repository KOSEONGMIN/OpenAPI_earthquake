package com.example.koseongmin.project01;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class JoinActivity extends AppCompatActivity{

    Button confirmButton,cancelButton;
    TextView userName, userId, userPassword, userPasswordCheck;
    DBHelper dbHelper;
    // DATABASE name
    private static final String DATABASE_NAME = "userDB.db";
    // DATABASE version
    private static final int DATABASE_VERSION = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        getSupportActionBar().setTitle("회원가입");

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SQLiteDB";
        File dir = new File(dirPath);

        if(!dir.exists()){
            dir.mkdirs();
        }
        String pathDir = dir.getAbsolutePath() + File.separator + DATABASE_NAME;
        String databaseName = pathDir.toString();


        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);
        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        userPassword = findViewById(R.id.beforePassword);
        userPasswordCheck = findViewById(R.id.afterPasswordView);


        // SQLite DB를 사용하기 위해 dbHelper 생성
        dbHelper = new DBHelper(this, databaseName, null, DATABASE_VERSION);




        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = userName.getText().toString();
                    String id = userId.getText().toString();
                    String password = userPassword.getText().toString();
                    String passwordCheck = userPasswordCheck.getText().toString();

                    if(password.equals(passwordCheck)){
                        // 테이블에 레코드(행) 추가
                        //   - 입력한 값을 파라미터로 dbHelper.insert() 메소드 호출
                        dbHelper.insert(name, id, password);
                        dbHelper.makeFamilyTable(id);

                        finish();
                    } else {
                        Toast.makeText(getApplication(), "비밀번호와 비밀번호확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplication(), "이름, 아이디, 비밀번호를 확인하세요!!" + e,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
