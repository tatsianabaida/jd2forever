package com.itacademy.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "homework", schema = "m_academy_storage")
public class Homework implements BaseEntity<Homework.HomeworkId> {

    @EmbeddedId
    private HomeworkId id;

    @Column(name = "work", nullable = false)
    private String work;

    @Column(name = "mark")
    @Enumerated(EnumType.STRING)
    private Mark mark;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embeddable
    public static class HomeworkId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "task_id")
        private Task task;

        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;
    }
}
