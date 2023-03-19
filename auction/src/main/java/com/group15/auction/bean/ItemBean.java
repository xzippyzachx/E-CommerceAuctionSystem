package com.group15.auction.bean;

import java.util.Objects;

public class ItemBean {

    private Integer itm_id;
    private String itm_name;
    private String itm_description;
    private Double itm_shipping_cost;

    public ItemBean(Integer itm_id, String itm_name, String itm_description, Double itm_shipping_cost) {
        this.itm_id = itm_id;
        this.itm_name = itm_name;
        this.itm_description = itm_description;
        this.itm_shipping_cost = itm_shipping_cost;
    }

    public ItemBean(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemBean itemBean = (ItemBean) o;
        return Objects.equals(itm_id, itemBean.itm_id) && Objects.equals(itm_name, itemBean.itm_name) && Objects.equals(itm_description, itemBean.itm_description) && Objects.equals(itm_shipping_cost, itemBean.itm_shipping_cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itm_id, itm_name, itm_description, itm_shipping_cost);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itm_id=" + itm_id +
                ", itm_name='" + itm_name + '\'' +
                ", itm_description='" + itm_description + '\'' +
                ", itm_shipping_cost=" + itm_shipping_cost +
                '}';
    }

}
