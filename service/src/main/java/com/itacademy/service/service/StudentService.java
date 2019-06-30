package com.itacademy.service.service;

import com.itacademy.database.dao.StudentDao;
import com.itacademy.database.entity.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {

    private final StudentDao studentDao;

    public List<Student> getAll() {
        return studentDao.findAll();
    }
}
