package com.example.bkap_sm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bksm.sqlite";
    private static final int DATABASE_VERSION = 4;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblClass (\n" +
                "    id   INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR (100) \n" +
                ");\n";
        db.execSQL(sql);

        sql = "CREATE TABLE tblStudent (\n" +
                "    id       INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    classId  INT           REFERENCES tblClass (id),\n" +
                "    name     VARCHAR (100),\n" +
                "    gender   BOOLEAN,\n" +
                "    email    VARCHAR (100),\n" +
                "    birthday DATE,\n" +
                "    phone    VARCHAR (10) \n" +
                ");\n";
        db.execSQL(sql);

        sql = "INSERT INTO tblClass(name) VALUES ('Java'),('PHP'),('C#'),('Android');";
        db.execSQL(sql);

        sql = "INSERT INTO tblStudent(classId, name, gender, email, birthday, phone) VALUES" +
                "(4, 'Nguyễn Tuấn Sơn', 1 , 'tson.nguyen3112@gmail.com', '1997-12-31', '0974079806'), \n" +
                "(1, 'Nguyễn Thành Đat', 1, 'datnthanh@gmail.com', '2000-05-24', '0986651243');";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 3 && newVersion == 4){
            String sql = "DROP TABLE tblStudent";
            db.execSQL(sql);

            sql = "CREATE TABLE tblStudent (\n" +
                    "    id       INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                    "    classId  INT           REFERENCES tblClass (id),\n" +
                    "    name     VARCHAR (100),\n" +
                    "    gender   BOOLEAN,\n" +
                    "    email    VARCHAR (100),\n" +
                    "    birthday DATE,\n" +
                    "    phone    VARCHAR (10) \n" +
                    ");\n";
            db.execSQL(sql);

            sql = "INSERT INTO tblStudent(classId, name, gender, email, birthday, phone) VALUES" +
                    "(4, 'Nguyễn Tuấn Sơn', 1 , 'tson.nguyen3112@gmail.com', '1997-12-31', '0974079806'), \n" +
                    "(1, 'Nguyễn Thành Đat', 1, 'datnthanh@gmail.com', '2000-05-24', '0986651243');";
            db.execSQL(sql);
        }
    }
}
