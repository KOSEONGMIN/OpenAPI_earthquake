package com.example.koseongmin.project01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class FamilyAddActivity extends AppCompatActivity {

    Button addButton,backButton;
    EditText familyName,familyPhone;
    DBHelper dbHelper;
    Intent intent;
    String loginId;

    private static final String DATABASE_NAME = "userDB.db";
    // DATABASE version
    private static final int DATABASE_VERSION = 1;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_add);

        intent = getIntent();
        loginId = intent.getStringExtra("id");
        familyName = findViewById(R.id.familyName);
        familyPhone = findViewById(R.id.familyPhone);

        getSupportActionBar().setTitle("가족 등록");

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SQLiteDB";
        File dir = new File(dirPath);

        if(!dir.exists()){
            dir.mkdirs();
        }
        String pathDir = dir.getAbsolutePath() + File.separator + DATABASE_NAME;
        String databaseName = pathDir.toString();
        // SQLite DB를 사용하기 위해 dbHelper 생성
        dbHelper = new DBHelper(this, databaseName, null, DATABASE_VERSION);

        addButton=findViewById(R.id.addButton);
        backButton=findViewById(R.id.backButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = familyName.getText().toString();
                String phone = familyPhone.getText().toString();

                dbHelper.familyInsert(loginId, name, phone);
                finish();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }//onCreate
}//FamilyActivity