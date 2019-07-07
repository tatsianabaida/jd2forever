package com.itacademy.service.service;

import com.itacademy.database.dao.StudentDao;
import com.itacademy.database.dao.UserDao;
import com.itacademy.database.dto.NewStudentDto;
import com.itacademy.database.entity.Person;
import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.Student;
import com.itacademy.service.exception.EmailExistsException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.itacademy.database.util.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginService implements UserDetailsService {

    private final UserDao userDao;
    private final StudentDao studentDao;
    private final PasswordEncoder passwordEncoder;

    public Long registerNewStudentAccount(NewStudentDto userDto) throws EmailExistsException {
        if (userDao.emailExists(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + userDto.getEmail());
        }
        com.itacademy.database.entity.User user = com.itacademy.database.entity.User.builder()
                .person(Person.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .build())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .build();
        String userCompany = isEmpty(userDto.getCompany()) ? null : userDto.getCompany();
        String userPosition = isEmpty(userDto.getCurrentPosition()) ? null : userDto.getCurrentPosition();
        Student newStudent = new Student(user, userCompany, userPosition);
        return studentDao.save(newStudent);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.of(email)
                .map(userDao::findByUsername)
                .map(user -> User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
