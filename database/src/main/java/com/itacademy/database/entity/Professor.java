package com.itacademy.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professor", schema = "m_academy_storage")
@PrimaryKeyJoinColumn(name = "user_id")
public class Professor extends User {

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "interests")
    private String interests;

    @Column(name = "working_experience_years")
    private Short workingExperienceYears;

    @OneToMany(mappedBy = "professor", orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

    public Professor(Person person, Person imaginaryPerson, String email, String password, Role role,
                     String speciality, String interests, Short workingExperienceYears) {
        super(person, imaginaryPerson, email, password, role);
        this.speciality = speciality;
        this.interests = interests;
        this.workingExperienceYears = workingExperienceYears;
    }
}

