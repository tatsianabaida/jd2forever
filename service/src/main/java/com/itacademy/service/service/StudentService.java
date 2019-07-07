package com.itacademy.service.service;

import com.itacademy.database.dao.StudentDao;
import com.itacademy.database.entity.Student;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class StudentService {

    private final StudentDao studentDao;

    public List<Student> getAll() {
        return studentDao.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentDao.findById(id);
    }
}
