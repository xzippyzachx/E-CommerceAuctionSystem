package com.group15.user.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="users")
public class User {

    @Id
    @SequenceGenerator(
            name = "usr_id_sequence",
            sequenceName = "usr_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usr_id_sequence"
    )
    private Integer usr_id;
    private String usr_username;

    public User(Integer usr_id,
                String usr_username) {

        this.usr_id = usr_id;
        this.usr_username = usr_username;
    }

    public User() {
    }

    public Integer getUsr_id() {
        return usr_id;
    }

    public String getUsr_username() {
        return usr_username;
    }

    public void setUsr_id(Integer usr_id) {
        this.usr_id = usr_id;
    }

    public void setUsr_username(String usr_username) {
        this.usr_username = usr_username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(usr_id, user.usr_id) && Objects.equals(usr_username, user.usr_username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usr_id, usr_username);
    }

    @Override
    public String toString() {
        return "User{" +
                "usr_id=" + usr_id +
                ", usr_username='" + usr_username + '\'' +
                '}';
    }
}
