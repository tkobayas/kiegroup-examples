package com.sample;

public class Order {

    private long id;
    private Customer customer;
    private String itemName;
    private int price;

    public Order() {}

    public Order(long id, Customer customer, String itemName, int price) {
        super();
        this.id = id;
        this.customer = customer;
        this.itemName = itemName;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customer=" + customer + ", itemName=" + itemName + ", price=" + price + "]";
    }

}
