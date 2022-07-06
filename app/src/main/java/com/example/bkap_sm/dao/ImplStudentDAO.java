package com.example.bkap_sm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bkap_sm.db.DatabaseHelper;
import com.example.bkap_sm.entity.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImplStudentDAO implements IStudentDAO{
    private Context mCtx;
    private SQLiteDatabase mDB;

    public ImplStudentDAO(Context mCtx) {
        this.mCtx = mCtx;
        DatabaseHelper helper = new DatabaseHelper(this.mCtx);
        mDB = helper.getWritableDatabase();
    }

    @SuppressLint("Range")
    @Override
    public List<Student> selectAll() {
        List<Student> data = new ArrayList<>();
        String sql = "SELECT * FROM tblStudent";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Cursor c = mDB.rawQuery(sql, null);
        while (c.moveToNext()){
            try {
                int id = c.getInt(c.getColumnIndex("id"));
                int classId = c.getInt(c.getColumnIndex("classId"));
                String name = c.getString(c.getColumnIndex("name"));
                Boolean gender = c.getInt(c.getColumnIndex("gender")) > 0;
                String email = c.getString(c.getColumnIndex("email"));
                Date birthday = fmt.parse(c.getString(c.getColumnIndex("birthday")));
                String phone = c.getString(c.getColumnIndex("phone"));
                Student st = new Student(id, classId, name, gender, email, birthday, phone);
                data.add(st);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @SuppressLint("Range")
    @Override
    public Student detail(int id) {
        String sql = "SELECT * FROM tblStudent WHERE id = ?";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Cursor c = mDB.rawQuery(sql, new String[]{String.valueOf(id)});
        while (c.moveToNext()){
            try {
                int classId = c.getInt(c.getColumnIndex("classId"));
                String name = c.getString(c.getColumnIndex("name"));
                Boolean gender = c.getInt(c.getColumnIndex("gender")) > 0;
                String email = c.getString(c.getColumnIndex("email"));
                Date birthday = fmt.parse(c.getString(c.getColumnIndex("birthday")));
                String phone = c.getString(c.getColumnIndex("phone"));
                Student st = new Student(id, classId, name, gender, email, birthday, phone);
                return st;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean insert(Student st) {
        ContentValues cv = new ContentValues();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        cv.put("classId", st.getClassId());
        cv.put("name", st.getName());
        cv.put("gender", st.isGender());
        cv.put("email", st.getEmail());
        cv.put("birthday", fmt.format(st.getBirthday()));
        cv.put("phone", st.getPhone());

        long newSt = mDB.insert("tblStudent", null, cv);
        if(newSt > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void delete(int id) {
        mDB.delete("tblStudent", "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public boolean update(Student st) {
        ContentValues cv = new ContentValues();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        cv.put("classId", st.getClassId());
        cv.put("name", st.getName());
        cv.put("gender", st.isGender());
        cv.put("email", st.getEmail());
        cv.put("birthday", fmt.format(st.getBirthday()));
        cv.put("phone", st.getPhone());
        int update = mDB.update("tblStudent", cv, "id = ?", new String[]{String.valueOf(st.getId())});
        if(update > 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    @Override
    public List<Student> search(int id, String name) {
        List<Student> data = new ArrayList<>();
        String sql = "SELECT * FROM tblStudent WHERE id = ? AND name LIKE ?";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Cursor c = mDB.rawQuery(sql, new String[]{String.valueOf(id), "%" + name + "%"});
        while (c.moveToNext()){
            try {
                int classId = c.getInt(c.getColumnIndex("classId"));
                String stName = c.getString(c.getColumnIndex("name"));
                Boolean gender = c.getInt(c.getColumnIndex("gender")) > 0;
                String email = c.getString(c.getColumnIndex("email"));
                Date birthday = fmt.parse(c.getString(c.getColumnIndex("birthday")));
                String phone = c.getString(c.getColumnIndex("phone"));
                Student st = new Student(id, classId, stName, gender, email, birthday, phone);
                data.add(st);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
