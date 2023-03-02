package com.example.search_app.capston;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.search_job_app.repositories.JobRepository;
import org.perscholas.search_job_app.repositories.MessageRepository;
import org.perscholas.search_job_app.repositories.SkillRepository;
import org.perscholas.search_job_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class MyCommandRunner implements CommandLineRunner {


     UserRepository userRepository;

     JobRepository jobRepository;

     SkillRepository skillRepository;

     MessageRepository messageRepository;

    @Autowired
    public MyCommandRunner(UserRepository userRepository, JobRepository jobRepository, SkillRepository skillRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.messageRepository = messageRepository;
    }



    @PostConstruct
    void created(){
        log.warn("==== My commandLineRunner get Created ===");
    }
    @Override
    public void run(String... args) throws Exception {

//        User user1 = new User(11,"M1", "m1@gmail.com");
//        User user2 = new User(22,"M2", "m2@gmail.com");
//        User user3 = new User(33,"M3", "m3@gmail.com");
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//
//        Job job1 = new Job(1, "j1", "co1");
//        Job job2 = new Job(2, "j2", "co2");
//        Job job3 = new Job(3, "j3", "co3");
//
//        jobRepository.save(job1);
//        jobRepository.save(job2);
//        jobRepository.save(job3);



    }
}
