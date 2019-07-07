package com.itacademy.database.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "courses", callSuper = true)
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

    public Professor(Person person, String email, String password, Role role, String speciality, String interests,
                     Short workingExperienceYears) {
        super(person, email, password, role);
        this.speciality = speciality;
        this.interests = interests;
        this.workingExperienceYears = workingExperienceYears;
    }

    public Professor(User user, String speciality, String interests,
                     Short workingExperienceYears) {
        super(user.getPerson(), user.getEmail(), user.getPassword(), user.getRole());
        this.speciality = speciality;
        this.interests = interests;
        this.workingExperienceYears = workingExperienceYears;
    }
}

