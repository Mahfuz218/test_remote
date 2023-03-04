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
    @Column(unique = true)
    String name;


    @ToString.Exclude
    @ManyToMany
    private Set<Users> user = new LinkedHashSet<>();




}
