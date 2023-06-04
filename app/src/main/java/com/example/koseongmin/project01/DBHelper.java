package com.example.koseongmin.project01;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{
    Context context;
    SQLiteDatabase db;
    Cursor cursor;


    //private static final String DATABASE_NAME = "userDB.db";
    private static final String TABLE_NAME = "userTable";
    //private static final int DATABASE_VERSION = 1;


    // DBHelper 생성자(context, DB name, cursor, DB version)
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //데이터베이스 이름과 버전 정보를 이용하여 상위        생성자를 호출

        super(context, name, null, version);
        this.context = context;
    }



    /* 데이터베이스 Create
       - 생성자에서 넘겨받은 이름의 DB와 버전의 DB가 존재하지 않을 때 한번 호출됨
       - 새로운 데이터베이스를 생성할 때 사용
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* 테이블을 생성하기 위해 SQL문을 작성하여 execSQL 문 실행
           - execSQL()메소드는 SELECT 문을 제외한 모든 SQL문을 실행
           - CREATE TABLE 테이블명 (컬럼명 타입 옵션);
        */

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, id TEXT, password TEXT);");

    }
    /* 데이터베이스 Open
       - DB를 열 때 호출됨
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
    }

    /* 데이터베이스 Version Upgrade
       - DB가 존재하지만 버전이 다르면 호출됨
       - DB를 변경하고, 버전을 변경할 때 여러가지 업그레이드 작업 수행 가능
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         /* 테이블을 업그레이드하기 위해 SQL문을 작성하여 execSQL 문 실행
           - DROP TABLE IF EXISTS 테이블명;
           - 기존 테이블을 삭제한 후 테이블 재 생성
        */

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void makeFamilyTable(String id){
        db = getWritableDatabase();

        db.execSQL("CREATE TABLE " + id + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, phone TEXT);");
    }

    //테이블의 레코드(행) Insert
    public void insert(String name, String id, String password) {
        db = getWritableDatabase();//데이터베이스를 write 모드로 open

        /* 레코드를 추가하기 위해 SQL문을 작성하여 execSQL 문 실행
           - INSERT INTO  테이블명 VALUES (컬럼값, 컬럼값..., 컬럼명, 컬럼명...);
           - 테이블에 레코드를 추가할 때 사용
        */

        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null, '" + name
                + "', '" + id + "', '" + password + "');");

        db.close();//DB close
    }

    public void familyInsert(String id, String name, String phone) {
        db = getWritableDatabase();//데이터베이스를 write 모드로 open

        /* 레코드를 추가하기 위해 SQL문을 작성하여 execSQL 문 실행
           - INSERT INTO  테이블명 VALUES (컬럼값, 컬럼값..., 컬럼명, 컬럼명...);
           - 테이블에 레코드를 추가할 때 사용
        */

        db.execSQL("INSERT INTO " + id + " VALUES(null, '" + name
                + "', '" + phone + "');");
        Toast.makeText(context, "가족 목록이 추가되었습니다.", Toast.LENGTH_LONG).show();
        db.close();//DB close
    }

    //테이블의 레코드 Update
    public void update(String name, String id, String password) {
        db = getWritableDatabase();//데이터베이스를 write 모드로 open

        /* 레코드를 갱신하기 위해 SQL문을 작성하여 execSQL 문 실행
           - UPDATE 테이블명 SET 변경할 컬럼 WHERE 조건 ;
           - 조건에 맞는 레코드를 갱신할 때 사용
        */

        db.execSQL("UPDATE " + TABLE_NAME + " SET name = '" + name
                + "', password = '" + password + "' WHERE id = '" + id + "';");

        db.close();//DB close
    }

    //테이블의 레코드 Delete
    public void delete(String name) {
        db = getWritableDatabase();//데이터베이스를 write 모드로 open

         /* 레코드를 삭제하기 위해 SQL문을 작성하여 execSQL 문 실행
           - DELETE FROM 테이블명 WHERE 조건;
           - 조건에 맞는 레코드를 삭제할 때 사용
         */

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE name = '" + name + "';");


        db.close();
    }

    public void familyDelete(String id, String name) {
        db = getWritableDatabase();//데이터베이스를 write 모드로 open

         /* 레코드를 삭제하기 위해 SQL문을 작성하여 execSQL 문 실행
           - DELETE FROM 테이블명 WHERE 조건;
           - 조건에 맞는 레코드를 삭제할 때 사용
         */

        db.execSQL("DELETE FROM " + id + " WHERE name = '" + name + "';");


        db.close();
    }

    //조건에 맞는 레코드 Select
    public String select(String id) {
        db = getReadableDatabase();//데이터베이스를 read 모드로 open
        String str = null;

        /* 레코드를 검색하기 위해 SELECT 문을 작성하여 rawQuery 문 실행
           - rawQuery()는 SELECT(쿼리) 결과를 Cursor 객체에 저장하는 SQL 실행 방법
           - SELECT 검색할 컬럼 FROM 테이블명 WHERE 조건;
           - 조건에 맞는 레코드를 검색할 때 사용
           - 테이블에서 조건에 맞는 레코드를 검색하여 cursor 객체에 저장
             (rawQuery()는 검색 결과를 cursor 객체에 반환)
         */

        cursor = db.rawQuery("SELECT password FROM " + TABLE_NAME
                + " WHERE id LIKE '" + id + "'", null);

        while (cursor.moveToNext()){
            str = cursor.getString(0);
        }
        cursor.close();


        return str;
    }

    public Cursor familySelect(String id) {
        db = getReadableDatabase();//데이터베이스를 read 모드로 open


        cursor = db.rawQuery("SELECT * FROM " + id,null);



        return cursor;
    }

    // 테이블에 있는 전체 레코드 쿼리
    public Cursor CursorQuery() {
        db = getReadableDatabase();

        // SELECT * FROM 테이블명 null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);


        return cursor;
    }
}
