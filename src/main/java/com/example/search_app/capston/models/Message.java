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
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "messages")
public class Message {
    @Id @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Size(max = 250)
    String messages;

    @NonNull
    @Size(max = 250)
    String senderName;

    @NonNull
    @Size(max = 250)
    String address;

    @Temporal(TemporalType.TIMESTAMP)
    Date sentAt = new Date();


    @ToString.Exclude
    @ManyToMany(mappedBy = "messages", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<User> user = new LinkedHashSet<>();

    public void addUser(User u){
        user.add(u);
        u.getMessages().add(this);
        log.debug("add user executed");
    }


    public void removeUser(User u){
        user.remove(u);
        u.getMessages().remove(this);
        log.debug("remove user executed");
    }


}
