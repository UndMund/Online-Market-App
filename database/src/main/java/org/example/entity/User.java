package org.example.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class User {
    private Long id;
    private String userName;
    private PositionUser position;
    private String email;
    private String phoneNumber;
    private String password;
    private BigDecimal money;

    public User() {
    }

    public User(Long id, String userName, PositionUser position, String email, String phoneNumber, String password, BigDecimal money) {
        this.id = id;
        this.userName = userName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && position == user.position && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(password, user.password) && Objects.equals(money, user.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, position, email, phoneNumber, password, money);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", userName='" + userName + '\'' +
               ", position=" + position +
               ", email='" + email + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", password='" + password + '\'' +
               ", money=" + money +
               '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PositionUser getPosition() {
        return position;
    }

    public void setPosition(PositionUser position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
