CREATE DATABASE m_academy_repository;

CREATE SCHEMA m_academy_storage;

SET SEARCH_PATH = m_academy_storage;

CREATE TABLE "user"
(
  id                          BIGSERIAL PRIMARY KEY,
  first_name                  VARCHAR(64) NOT NULL,
  last_name                   VARCHAR(64) NOT NULL,
  imaginary_person_first_name VARCHAR(64),
  imaginary_person_last_name  VARCHAR(64),
  email                       VARCHAR(64) NOT NULL,
  password                    VARCHAR(64) NOT NULL,
  role                        VARCHAR(8)  NOT NULL DEFAULT 'USER'
);

CREATE TABLE student
(
  id               BIGSERIAL PRIMARY KEY,
  company          VARCHAR(64),
  current_position VARCHAR(128),
  user_id          BIGINT UNIQUE REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE professor
(
  id                       BIGSERIAL PRIMARY KEY,
  speciality               VARCHAR(128),
  interests                TEXT,
  working_experience_years SMALLINT,
  user_id                  BIGINT UNIQUE REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE course
(
  id           BIGSERIAL PRIMARY KEY,
  name         VARCHAR(128) NOT NULL,
  professor_id BIGINT       NOT NULL REFERENCES professor (id)
);

CREATE TABLE course_student
(
  course_id  BIGINT REFERENCES course (id) ON DELETE CASCADE,
  student_id BIGINT REFERENCES student (id) ON DELETE CASCADE,
  PRIMARY KEY (course_id, student_id)
);

CREATE TABLE task
(
  id        BIGSERIAL PRIMARY KEY,
  subject   VARCHAR(64) NOT NULL,
  exercise  TEXT        NOT NULL,
  course_id BIGINT      NOT NULL REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE homework
(
  task_id    BIGINT REFERENCES task (id),
  student_id BIGINT REFERENCES student (id) ON DELETE CASCADE,
  work       TEXT NOT NULL,
  mark       VARCHAR(16),
  PRIMARY KEY (task_id, student_id)
);