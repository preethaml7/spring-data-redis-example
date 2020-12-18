package org.protopanda.redis;

import lombok.RequiredArgsConstructor;
import org.protopanda.redis.model.Student;
import org.protopanda.redis.model.Student.Gender;
import org.protopanda.redis.repository.StudentRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
public class RedisApplication implements ApplicationRunner {

    private final StudentRepository studentRepository;

    Instant startTime;
    Instant stopTime;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(RedisApplication.class, args)));
    }

    public void run(ApplicationArguments args) {

        Student student = new Student("EP10101", "John Carter", Gender.MALE, 1);
        this.startTime = Instant.now();
        this.studentRepository.save(student);
        this.stopTime = Instant.now();
        System.out.println("Redis Save Time(ms): " + Duration.between(this.startTime, this.stopTime).toMillis());

        this.startTime = Instant.now();
        Student retrievedStudent = (Student) this.studentRepository.findById("EP10101").get();
        this.stopTime = Instant.now();
        System.out.println("Redis record retrieved: " + retrievedStudent.toString());
        System.out.println("Redis Find Record Time(ms): " + Duration.between(this.startTime, this.stopTime).toMillis());

        this.startTime = Instant.now();
        Student updatedStudent = (Student) this.studentRepository.findById("EP10101").get();
        updatedStudent.setGrade(4);
        updatedStudent.setId("EP10107");
        this.studentRepository.save(updatedStudent);
        this.stopTime = Instant.now();
        System.out.println("Redis record retrieved: " + retrievedStudent.toString());
        System.out.println("Redis Find & Update Record  Time(ms): " + Duration.between(this.startTime, this.stopTime).toMillis());

        this.startTime = Instant.now();
        this.studentRepository.delete(updatedStudent);
        this.stopTime = Instant.now();
        System.out.println("Redis Record Deletion Time(ms): " + Duration.between(this.startTime, this.stopTime).toMillis());

    }
}