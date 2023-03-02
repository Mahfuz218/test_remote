package com.example.search_app.capston.models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String addressOne;
    @NonNull
    String addressTwo;
    @NonNull
    String city;
    @NonNull
    String state;
    @NonNull
    String zipCode;


    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    Set<User> user = new LinkedHashSet<>();

    public void addUser(User user){
        user.add(user);
        user.getAddresses().add(this);
        log.debug("add user executed");
    }

    public void removeUser(User user){
        user.remove(user);
        user.getAddresses().remove(this);
        log.debug("remove user executed");
    }
}