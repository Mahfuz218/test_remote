package com.example.search_app.capston.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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

@Table(name = "skills")
public class Skill {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @NonNull
    @Size(max = 50)
    String name;


    @ToString.Exclude
    @ManyToMany(mappedBy = "skills", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<User> user = new LinkedHashSet<>();


    public void addUser(User s){
        user.add(s);
        s.getSkills().add(this);
        log.debug("add user executed");
    }


    public void removeUser(User s){
        user.remove(s);
        s.getSkills().remove(this);
        log.debug("add remove executed");
    }



}