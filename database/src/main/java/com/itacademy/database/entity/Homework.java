package com.itacademy.database.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@Entity
@Table(name = "homework", schema = "m_academy_storage")
public class Homework implements BaseEntity<HomeworkId> {

    @EmbeddedId
    private HomeworkId id;

    @Column(name = "work", nullable = false)
    private String work;

    @Column(name = "mark")
    @Enumerated(EnumType.STRING)
    private Mark mark;
}
