package org.example.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private StatusProduct status;
    private CategoryProduct category;
    private User user;

    public Product(Long id, String name, BigDecimal price, String description, StatusProduct status, CategoryProduct category, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
        this.category = category;
        this.user = user;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", description='" + description + '\'' +
               ", status=" + status +
               ", category=" + category +
               ", seller=" + user.toString() +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(description, product.description) && status == product.status && category == product.category && Objects.equals(user, product.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, status, category, user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusProduct getStatus() {
        return status;
    }

    public void setStatus(StatusProduct status) {
        this.status = status;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
