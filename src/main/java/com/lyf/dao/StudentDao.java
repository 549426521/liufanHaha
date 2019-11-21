package com.lyf.dao;

import com.lyf.po.StudentEntity;

import java.util.List;

public interface StudentDao {
    List<StudentEntity> selectAll();
}
