package com.example.search_app.capston.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

/**
 * @author Touhid Hossain
 */
@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_ID_SEQUENCE")
    @SequenceGenerator(name = "ROLE_ID_SEQUENCE", sequenceName = "ROLE_ID_SEQ_GEN", initialValue = 1, allocationSize = 1)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roleList")
    private Set<Users> usersList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
