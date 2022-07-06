package com.example.bkap_sm.dao;

import com.example.bkap_sm.entity.Classes;

import java.util.List;

public interface IClassDAO {
    public List<Classes> selectAll();
    public Classes detail(int id);
}
