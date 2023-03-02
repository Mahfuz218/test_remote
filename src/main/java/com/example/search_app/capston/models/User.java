package com.example.search_app.capston.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

@Getter
@Setter
@Slf4j
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;


    static String firstName;

    String lastName;


    String email;

    String password;

    @Size(max = 15)
    String phoneNumber;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, Image image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = image;
    }


    public User(String firstName, String lastName, String email, String password, Set<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.addresses = addresses;
    }

    public User(Long id, String firstName, String lastName, String email, String password, Set<Job> jobs) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.jobs = jobs;
    }

    public User( String lastName, String email, String password, Set<Skill> skills) {

        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.skills = skills;
    }

    public User(String firstName, String lastName, String email, String password, Set<Message> messages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.messages = messages;
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, Set<Job> jobs, Image image, Set<Address> addresses, Set<Message> messages, Set<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.jobs = jobs;
        this.image = image;
        this.addresses = addresses;
        this.messages = messages;
        this.skills = skills;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "user_job",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    Set<Job> jobs = new LinkedHashSet<>();

    public void addJob(Job j){
        jobs.add(j);
        j.getUser().add(this);
        log.debug("add jobs executed");
    }


    public void removeJob(Job j){
        jobs.remove(j);
        j.getUser().remove(this);
        log.debug("remove jobs executed");
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    Set<Address> addresses = new LinkedHashSet<>();


    public void addAddress(Address address){
        addresses.add(address);
        address.getUser().add(this);
        log.debug("add address executed");
    }

    public void removeAddress(Address address){
        addresses.remove(address);
        address.getUser().remove(this);
        log.debug("remove address executed");
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "user_messages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "messages_id"))
    Set<Message> messages = new LinkedHashSet<>();


    public void addMessage(Message m){
        messages.add(m);
        m.getUser().add(this);
        log.debug("add message executed");
    }

    public void removeMessage(Message m){
        messages.remove(m);
        m.getUser().remove(this);
        log.debug("remove message executed");
    }



    @ManyToMany
    @JoinTable(name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id"))
    Set<Skill> skills = new LinkedHashSet<>();


    public void addSkill(Skill s){
        skills.add(s);
        s.getUser().add(this);
        log.debug("add skills executed");
    }

    public void removeSkill(Skill s){
        skills.remove(s);
        s.getUser().remove(this);
        log.debug("remove skills executed");
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User myUser = (User) o;
        return Objects.equals(firstName, User.firstName) && Objects.equals(lastName, myUser.lastName)
                && Objects.equals(email, myUser.email) && Objects.equals(password, myUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password);
    }


    public void remove(User user) {
    }

    public void add(User user) {
    }
}