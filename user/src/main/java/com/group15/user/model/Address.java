package com.group15.user.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="addresses")
public class Address {

    @Id
    @SequenceGenerator(
            name = "adr_id_sequence",
            sequenceName = "adr_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "adr_id_sequence"
    )
    private Integer adr_id;
    private String adr_street_name;
    private Integer adr_street_number;
    private String adr_city;
    private String adr_province;
    private String adr_country;
    private String adr_postal_code;

    public Address(Integer adr_id, String adr_street_name, Integer adr_street_number, String adr_city, String adr_province, String adr_country, String adr_postal_code) {
        this.adr_id = adr_id;
        this.adr_street_name = adr_street_name;
        this.adr_street_number = adr_street_number;
        this.adr_city = adr_city;
        this.adr_province = adr_province;
        this.adr_country = adr_country;
        this.adr_postal_code = adr_postal_code;
    }

    public Address() {}

    public Integer getAdr_id() {
        return adr_id;
    }

    public void setAdr_id(Integer adr_id) {
        this.adr_id = adr_id;
    }

    public String getAdr_street_name() {
        return adr_street_name;
    }

    public void setAdr_street_name(String adr_street_name) {
        this.adr_street_name = adr_street_name;
    }

    public Integer getAdr_street_number() {
        return adr_street_number;
    }

    public void setAdr_street_number(Integer adr_street_number) {
        this.adr_street_number = adr_street_number;
    }

    public String getAdr_city() {
        return adr_city;
    }

    public void setAdr_city(String adr_city) {
        this.adr_city = adr_city;
    }

    public String getAdr_province() {
        return adr_province;
    }

    public void setAdr_province(String adr_province) {
        this.adr_province = adr_province;
    }

    public String getAdr_country() {
        return adr_country;
    }

    public void setAdr_country(String adr_country) {
        this.adr_country = adr_country;
    }

    public String getAdr_postal_code() {
        return adr_postal_code;
    }

    public void setAdr_postal_code(String adr_postal_code) {
        this.adr_postal_code = adr_postal_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(adr_id, address.adr_id) && Objects.equals(adr_street_name, address.adr_street_name) && Objects.equals(adr_street_number, address.adr_street_number) && Objects.equals(adr_city, address.adr_city) && Objects.equals(adr_province, address.adr_province) && Objects.equals(adr_country, address.adr_country) && Objects.equals(adr_postal_code, address.adr_postal_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adr_id, adr_street_name, adr_street_number, adr_city, adr_province, adr_country, adr_postal_code);
    }

    @Override
    public String toString() {
        return "Address{" +
                "adr_id=" + adr_id +
                ", adr_street_name='" + adr_street_name + '\'' +
                ", adr_street_number=" + adr_street_number +
                ", adr_city='" + adr_city + '\'' +
                ", adr_province='" + adr_province + '\'' +
                ", adr_country='" + adr_country + '\'' +
                ", adr_postal_code='" + adr_postal_code + '\'' +
                '}';
    }
}
