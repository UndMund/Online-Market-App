package org.example.dao;

import org.example.entity.PositionUser;
import org.example.entity.User;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
       // System.out.println(UserDao.getINSTANCE().save(new User(3L, "c", PositionUser.USER, "ff", " ", "sfsd", BigDecimal.ONE)).getId());
        System.out.println(UserDao.getINSTANCE().delete(5L));

    }
}
