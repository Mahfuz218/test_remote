package com.example.search_app.capston.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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

    String companyName;
    @NonNull
    String location;

    @NonNull
    @Size(max = 50)
    String jobType;


    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt = new Date();

    @ToString.Exclude
    @ManyToMany(mappedBy = "jobs", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    Set<User> user = new LinkedHashSet<>();



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
