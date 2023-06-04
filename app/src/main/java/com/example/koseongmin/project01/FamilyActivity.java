package com.example.koseongmin.project01;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    Intent intent;
    Button addButton;
    String loginId;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> phoneNums = new ArrayList<>();
    CustomAdapter adapter;

    // DATABASE name
    private static final String DATABASE_NAME = "userDB.db";
    // DATABASE version
    private static final int DATABASE_VERSION = 1;


    @Override
    protected void onResume() {
        super.onResume();

        name = new ArrayList<>();
        phoneNums = new ArrayList<>();

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SQLiteDB";
        File dir = new File(dirPath);

        if(!dir.exists()){
            dir.mkdirs();
        }
        String pathDir = dir.getAbsolutePath() + File.separator + DATABASE_NAME;
        String databaseName = pathDir.toString();
        // SQLite DB를 사용하기 위해 dbHelper 생성
        dbHelper = new DBHelper(this, databaseName, null, DATABASE_VERSION);
        Cursor cursor = dbHelper.familySelect(loginId);

        while (cursor.moveToNext()) {
            name.add(cursor.getString(1));
            phoneNums.add(cursor.getString(2));
        }
        cursor.close();

        adapter = new CustomAdapter(this, 0, name, phoneNums);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        getSupportActionBar().setTitle("가족 목록");

        intent = getIntent();
        loginId = intent.getStringExtra("id");

        listView = (ListView)this.findViewById(R.id.list);
        addButton = (Button)findViewById(R.id.addButton);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFamilyIntent = new Intent(getApplicationContext(), FamilyAddActivity.class);
                addFamilyIntent.putExtra("id", loginId);
                startActivity(addFamilyIntent);
            }
        });
    }

    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> name, ArrayList<String> phoneNums) {
            super(context, textViewResourceId, name);
        }



        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.item_data, null);
            }


            TextView textView = (TextView)v.findViewById(R.id.item_name);
            textView.setText(name.get(position));

            final String text = name.get(position);
            ImageView phoneImage = (ImageView) v.findViewById(R.id.item_phone);
            phoneImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phoneNums.get(position)));
                    startActivity(myIntent);
                }
            });
            ImageView removeImage = (ImageView) v.findViewById(R.id.item_remove);
            removeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.familyDelete(loginId, name.get(position));
                    updateListView();
                    Toast.makeText(getContext(), "가족 목록이 삭제되었습니다.", Toast.LENGTH_LONG);
                }
            });



            return v;
        }

        public void updateListView(){
            Cursor cursor = dbHelper.familySelect(loginId);
            name = new ArrayList<>();
            phoneNums = new ArrayList<>();

            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                phoneNums.add(cursor.getString(2));
            }
            cursor.close();

            adapter = new CustomAdapter(getContext(), 0, name, phoneNums);
            listView.setAdapter(adapter);

        }
    }
}