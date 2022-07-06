package com.example.bkap_sm.entity;

import java.util.Date;

public class Student {
    private int id;
    private int classId;
    private String name;
    private boolean gender;
    private String email;
    private Date birthday;
    private String phone;

    public Student() {
    }

    public Student(int id, int classId, String name, boolean gender, String email, Date birthday, String phone) {
        this.id = id;
        this.classId = classId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Student(int classId, String name, boolean gender, String email, Date birthday, String phone) {
        this.classId = classId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
