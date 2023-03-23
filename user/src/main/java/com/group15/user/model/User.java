package com.group15.user.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
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

    @Column(nullable = false, unique = true)
    private String usr_username;

    @Column(nullable = false)
    private String usr_password;

    @Column(nullable = false)
    private String usr_first_name;

    @Column(nullable = false)
    private String usr_last_name;

    @Column(nullable = false)
    private String usr_street_name;

    @Column(nullable = false)
    private Integer usr_street_number;

    @Column(nullable = false)
    private String usr_city;

    @Column(nullable = false)
    private String usr_province;

    @Column(nullable = false)
    private String usr_country;

    @Column(nullable = false)
    private String usr_postal_code;
//    @OneToOne
//    @JoinColumn(name = "usr_adr_id")
//    private Address usr_adr_id;

    public User(Integer usr_id, String usr_username,
                String usr_password, String usr_first_name,
                String usr_last_name, String usr_street_name,
                Integer usr_street_number,
                String usr_city, String usr_province,
                String usr_country, String usr_postal_code
    ) {
        this.usr_id = usr_id;
        this.usr_username = usr_username;
        this.usr_password = usr_password;
        this.usr_first_name = usr_first_name;
        this.usr_last_name = usr_last_name;
        this.usr_street_name = usr_street_name;
        this.usr_street_number = usr_street_number;
        this.usr_city = usr_city;
        this.usr_province = usr_province;
        this.usr_country = usr_country;
        this.usr_postal_code = usr_postal_code;
    }

    public User() {
    }

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


    public String getUsr_street_name() {
        return usr_street_name;
    }

    public void setUsr_street_name(String usr_street_name) {
        this.usr_street_name = usr_street_name;
    }

    public Integer getUsr_street_number() {
        return usr_street_number;
    }

    public void setUsr_street_number(Integer usr_street_number) {
        this.usr_street_number = usr_street_number;
    }

    public String getUsr_city() {
        return usr_city;
    }

    public void setUsr_city(String usr_city) {
        this.usr_city = usr_city;
    }

    public String getUsr_province() {
        return usr_province;
    }

    public void setUsr_province(String usr_province) {
        this.usr_province = usr_province;
    }

    public String getUsr_country() {
        return usr_country;
    }

    public void setUsr_country(String usr_country) {
        this.usr_country = usr_country;
    }

    public String getUsr_postal_code() {
        return usr_postal_code;
    }

    public void setUsr_postal_code(String usr_postal_code) {
        this.usr_postal_code = usr_postal_code;
    }


    @Override
    public boolean equals(Object o) { //have not added address to equals()
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(usr_id, user.usr_id) && Objects.equals(usr_username, user.usr_username) && Objects.equals(usr_password, user.usr_password) && Objects.equals(usr_first_name, user.usr_first_name) && Objects.equals(usr_last_name, user.usr_last_name);
    }

    @Override
    public int hashCode() { //have not added address to the hascode()
        return Objects.hash(usr_id, usr_username, usr_password, usr_first_name, usr_last_name);
    }

    @Override
    public String toString() {
        return "User{" +
                "usr_id=" + usr_id +
                ", usr_username='" + usr_username + '\'' +
                ", usr_password='" + usr_password + '\'' +
                ", usr_first_name='" + usr_first_name + '\'' +
                ", usr_last_name='" + usr_last_name + '\'' +
                ", adr_street_name='" + usr_street_name + '\'' +
                ", adr_street_number=" + usr_street_number +
                ", adr_city='" + usr_city + '\'' +
                ", adr_province='" + usr_province + '\'' +
                ", adr_country='" + usr_country + '\'' +
                ", adr_postal_code='" + usr_postal_code +
                '}';
    }
}
