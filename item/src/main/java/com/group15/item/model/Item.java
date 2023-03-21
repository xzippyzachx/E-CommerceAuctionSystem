package com.group15.item.model;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name="items")
public class Item {

    @Id
    @SequenceGenerator(
            name = "itm_id_sequence",
            sequenceName = "itm_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "itm_id_sequence"
    )
    private Integer itm_id;
    private String itm_name;
    private String itm_description;

    private Double itm_shipping_cost;

    private Double itm_expedited_cost;

    public Item(Integer itm_id, String itm_name, String itm_description, Double itm_shipping_cost, Double itm_expedited_cost) {
        this.itm_id = itm_id;
        this.itm_name = itm_name;
        this.itm_description = itm_description;
        this.itm_shipping_cost = itm_shipping_cost;
        this.itm_expedited_cost = itm_expedited_cost;
    }

    public Item() {

    }

    public Integer getItm_id() {
        return itm_id;
    }

    public void setItm_id(Integer itm_id) {
        this.itm_id = itm_id;
    }

    public String getItm_name() {
        return itm_name;
    }

    public void setItm_name(String itm_name) {
        this.itm_name = itm_name;
    }

    public String getItm_description() {
        return itm_description;
    }

    public void setItm_description(String itm_description) {
        this.itm_description = itm_description;
    }

    public Double getItm_shipping_cost() {
        return itm_shipping_cost;
    }

    public void setItm_shipping_cost(Double itm_shipping_cost) {
        this.itm_shipping_cost = itm_shipping_cost;
    }

    public Double getItm_expedited_cost() {
        return itm_expedited_cost;
    }

    public void setItm_expedited_cost(Double itm_expedited_cost) {
        this.itm_expedited_cost = itm_expedited_cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itm_id, item.itm_id) && Objects.equals(itm_name, item.itm_name) && Objects.equals(itm_description, item.itm_description) && Objects.equals(itm_shipping_cost, item.itm_shipping_cost) && Objects.equals(itm_expedited_cost, item.itm_expedited_cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itm_id, itm_name, itm_description, itm_shipping_cost, itm_expedited_cost);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itm_id=" + itm_id +
                ", itm_name='" + itm_name + '\'' +
                ", itm_description='" + itm_description + '\'' +
                ", itm_shipping_cost=" + itm_shipping_cost +
                ", itm_expedited_cost=" + itm_expedited_cost +
                '}';
    }
}
