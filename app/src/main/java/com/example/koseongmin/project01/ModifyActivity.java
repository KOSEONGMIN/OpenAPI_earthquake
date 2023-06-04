package com.example.koseongmin.project01;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class ModifyActivity extends AppCompatActivity {
    Button confirmButton,cancelButton;
    EditText modifyName, beforePassword, afterPassword;
    DBHelper dbHelper;
    // DATABASE name
    private static final String DATABASE_NAME = "userDB.db";
    // DATABASE version
    private static final int DATABASE_VERSION = 1;
    Intent intent;
    String loginId;
    String loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        getSupportActionBar().setTitle("회원정보 수정");



        intent = getIntent();
        loginId = intent.getStringExtra("id");
        loginPassword = intent.getStringExtra("password");

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SQLiteDB";
        File dir = new File(dirPath);

        if(!dir.exists()){
            dir.mkdirs();
        }
        String pathDir = dir.getAbsolutePath() + File.separator + DATABASE_NAME;
        String databaseName = pathDir.toString();

        modifyName = findViewById(R.id.userName);
        beforePassword = findViewById(R.id.beforePassword);
        afterPassword = findViewById(R.id.afterPassword);
        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);

        // SQLite DB를 사용하기 위해 dbHelper 생성
        dbHelper = new DBHelper(this, databaseName, null, DATABASE_VERSION);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = modifyName.getText().toString();
                    String afterPW = afterPassword.getText().toString();

                    if(checkPassword()){
                        // 테이블에 레코드(행) 추가
                        //   - 입력한 값을 파라미터로 dbHelper.insert() 메소드 호출
                        dbHelper.update(name, loginId, afterPW);
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

            }
        });
    }

    public boolean checkPassword() {
        // DB에 입력된 ID와 Password가 일치하는지 확인하는 구문
        boolean checkPassword = false;
        String str = dbHelper.select(loginId);
        if(str.equals(beforePassword.getText().toString())) {
            checkPassword = true;
        }
        return checkPassword;
    }
}
