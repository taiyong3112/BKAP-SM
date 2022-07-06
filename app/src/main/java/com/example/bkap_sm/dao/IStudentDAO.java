package com.example.bkap_sm.dao;

import com.example.bkap_sm.entity.Student;

import java.util.List;

public interface IStudentDAO {
    public List<Student> selectAll();
    public Student detail(int id);
    public boolean insert(Student st);
    public void delete(int id);
    public boolean update(Student st);
    public List<Student> search(int id, String name);
}
