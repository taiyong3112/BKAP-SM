package com.example.bkap_sm.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bkap_sm.db.DatabaseHelper;
import com.example.bkap_sm.entity.Classes;

import java.util.ArrayList;
import java.util.List;

public class ImplClassDAO implements IClassDAO{

    private Context mCtx;
    private SQLiteDatabase mDB;

    public ImplClassDAO(Context mCtx) {
        this.mCtx = mCtx;
        DatabaseHelper helper = new DatabaseHelper(this.mCtx);
        mDB = helper.getWritableDatabase();
    }

    @SuppressLint("Range")
    @Override
    public List<Classes> selectAll() {
        List<Classes> data = new ArrayList<>();
        String sql = "SELECT * FROM tblClass";
        Cursor c = mDB.rawQuery(sql, null);
        while (c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            Classes cl = new Classes(id, name);
            data.add(cl);
        }
        return data;
    }

    @SuppressLint("Range")
    @Override
    public Classes detail(int id) {
        String sql = "SELECT * FROM tblClass WHERE id = ?";
        Cursor c = mDB.rawQuery(sql, new String[]{String.valueOf(id)});
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex("name"));
            Classes cl = new Classes(id, name);
            return cl;
        }
        return null;
    }
}
