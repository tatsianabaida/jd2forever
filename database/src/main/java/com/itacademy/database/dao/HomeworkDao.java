package com.itacademy.database.dao;

import com.itacademy.database.entity.Homework;
import com.itacademy.database.entity.HomeworkId;
import com.itacademy.database.filter.HomeworkFilter;
import org.springframework.stereotype.Repository;

@Repository
public class HomeworkDao extends BaseDaoFilterable<HomeworkId, Homework, HomeworkFilter> {
}
