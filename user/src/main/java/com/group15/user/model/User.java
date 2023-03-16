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
    private String usr_password;
    private String usr_first_name;
    private String usr_last_name;
    @OneToOne
    @JoinColumn(name = "usr_adr_id")
    private Address usr_adr_id;

    public User(Integer usr_id, String usr_username, String usr_password, String usr_first_name, String usr_last_name, Address usr_adr_id) {
        this.usr_id = usr_id;
        this.usr_username = usr_username;
        this.usr_password = usr_password;
        this.usr_first_name = usr_first_name;
        this.usr_last_name = usr_last_name;
        this.usr_adr_id = usr_adr_id;
    }

    public User() {}

    public Integer getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(Integer usr_id) {
        this.usr_id = usr_id;
    }

    public String getUsr_username() {
        return usr_username;
    }

    public void setUsr_username(String usr_username) {
        this.usr_username = usr_username;
    }

    public String getUsr_password() {
        return usr_password;
    }

    public void setUsr_password(String usr_password) {
        this.usr_password = usr_password;
    }

    public String getUsr_first_name() {
        return usr_first_name;
    }

    public void setUsr_first_name(String usr_first_name) {
        this.usr_first_name = usr_first_name;
    }

    public String getUsr_last_name() {
        return usr_last_name;
    }

    public void setUsr_last_name(String usr_last_name) {
        this.usr_last_name = usr_last_name;
    }

    public Address getUsr_adr_id() {
        return usr_adr_id;
    }

    public void setUsr_adr_id(Address usr_adr_id) {
        this.usr_adr_id = usr_adr_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(usr_id, user.usr_id) && Objects.equals(usr_username, user.usr_username) && Objects.equals(usr_password, user.usr_password) && Objects.equals(usr_first_name, user.usr_first_name) && Objects.equals(usr_last_name, user.usr_last_name) && Objects.equals(usr_adr_id, user.usr_adr_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usr_id, usr_username, usr_password, usr_first_name, usr_last_name, usr_adr_id);
    }

    @Override
    public String toString() {
        return "User{" +
                "usr_id=" + usr_id +
                ", usr_username='" + usr_username + '\'' +
                ", usr_password='" + usr_password + '\'' +
                ", usr_first_name='" + usr_first_name + '\'' +
                ", usr_last_name='" + usr_last_name + '\'' +
                ", usr_adr_id=" + usr_adr_id +
                '}';
    }
}
