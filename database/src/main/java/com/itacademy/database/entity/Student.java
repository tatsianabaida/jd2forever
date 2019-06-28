package com.itacademy.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "homeworks", callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student", schema = "m_academy_storage")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "company")
    private String company;

    @Column(name = "current_position")
    private String currentPosition;

    @OneToMany(mappedBy = "id.student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Homework> homeworks = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "course_student", schema = "m_academy_storage",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    public Student(Person person, String email, String password, Role role, String company, String currentPosition) {
        super(person, email, password, role);
        this.company = company;
        this.currentPosition = currentPosition;
    }

    public Student(User user, String company, String currentPosition) {
        super(user.getPerson(), user.getEmail(), user.getPassword(), user.getRole());
        this.company = company;
        this.currentPosition = currentPosition;
    }
}
