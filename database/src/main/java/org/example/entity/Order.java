package org.example.entity;

public class Order {
    private Long id;
    private Product product;
    private User user;

    public Order() {
    }

    public Order(Long id, Product product, User user) {
        this.id = id;
        this.product = product;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", product=" + product.toString() +
               ", customer=" + user.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
