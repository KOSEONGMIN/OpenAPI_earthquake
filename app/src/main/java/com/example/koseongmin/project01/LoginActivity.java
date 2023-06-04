package com.example.koseongmin.project01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    Button login, login_join;
    EditText loginId, loginPassword;


    Cursor cursor = null;
    DBHelper dbHelper;
    // DATABASE name
    private static final String DATABASE_NAME = "userDB.db";
    // DATABASE version
    private static final int DATABASE_VERSION = 1;
    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SQLiteDB";
    File dir = new File(dirPath);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        login_join = (Button) findViewById(R.id.login_join);
        loginId = (EditText) findViewById(R.id.loginId);
        loginPassword = (EditText) findViewById(R.id.loginPassword);



        if(!dir.exists()){
            dir.mkdirs();
        }
        String pathDir = dir.getAbsolutePath() + File.separator + DATABASE_NAME;
        String databaseName = pathDir.toString();

        dbHelper = new DBHelper(this, databaseName, null, DATABASE_VERSION);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginId == null | loginPassword == null){
                    Toast.makeText(getApplication(), "id와 password를 입력하세요.",Toast.LENGTH_LONG).show();
                } else {
                    if(!checkId()){
                        Toast.makeText(getApplication(),"가입되지 않은 아이디 입니다", Toast.LENGTH_LONG).show();
                    } else{
                        if(!checkPassword()){
                            Toast.makeText(getApplication(),"패스워드가 틀립니다", Toast.LENGTH_LONG).show();
                        } else{
                            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                            intent.putExtra("id", loginId.getText().toString());
                            intent.putExtra("password", loginPassword.getText().toString());
                            startActivity(intent);
                        }
                    }
                }


            }
        });

        login_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean checkId(){
        // DB에 ID 있는지 확인하는 구문
        boolean checkId = false;
        String str = dbHelper.select(loginId.getText().toString());
        if (str != null){
            checkId = true;
        }
        return checkId;
    }

    public boolean checkPassword() {
        // DB에 입력된 ID와 Password가 일치하는지 확인하는 구문
        boolean checkPassword = false;
        String str = dbHelper.select(loginId.getText().toString());
        if(str.equals(loginPassword.getText().toString())) {
            checkPassword = true;
        }
        return checkPassword;
    }
}
