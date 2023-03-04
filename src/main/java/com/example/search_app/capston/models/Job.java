package com.example.search_app.capston.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "jobs")
public class Job {
    @Id @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String title;

    @Column(length = 2500)
    String jobDescription;

    @ManyToOne
    Company company;

    @ManyToMany
    Set<Skill> skills;

    @NonNull
    private String location;

    @NonNull
    @Size(max = 50)
    private String jobType;


    private LocalDate createdAt = LocalDate.now();

    @JoinColumn(name = "created_by")
    @ManyToOne
    private Users createBy;


//     public void addUser(User u){
//         u.addUser(u);
//         u.getUser().add(this);
//         log.debug("add user executed");
//     }
//
//
//    public void removeUser(User u){
//        u.removeUser(u);
//        u.getUser().remove(this);
//        log.debug("remove user executed");
//    }



}
