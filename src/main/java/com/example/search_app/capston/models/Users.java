package com.example.search_app.capston.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Touhid Hossain
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USERS")
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_ID_SEQUENCE")
    @SequenceGenerator(name = "USERS_ID_SEQUENCE", sequenceName = "USERS_ID_SEQ_GEN", initialValue = 1, allocationSize = 1)
    private long id;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, updatable = false, length = 255, unique = true)
    private String username;

    @Column(nullable = false, updatable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean accountNonExpired;

    @Column(nullable = false)
    private boolean accountNonLocked;

    @Column(nullable = false)
    private boolean credentialsNonExpired;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "USERS_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            foreignKey = @ForeignKey(name = "USERS_ROLE_FK"),
            inverseForeignKey = @ForeignKey(name = "ROLE_USERS_FK"))
    private Set<Role> roleList;

    public Users(Users users) {
        this.id = users.getId();
        this.password = users.getPassword();
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.accountNonExpired = users.isAccountNonExpired();
        this.accountNonLocked = users.isAccountNonLocked();
        this.credentialsNonExpired = users.isCredentialsNonExpired();
        this.enabled = users.isEnabled();
        this.roleList = users.getRoleList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && username.equals(users.username) && email.equals(users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

}
