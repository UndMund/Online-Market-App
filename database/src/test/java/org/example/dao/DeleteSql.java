package org.example.dao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DeleteSql {
    String DELETE_USERS_SQL = """               
                TRUNCATE TABLE users CASCADE;                                           
                """;
    String DELETE_PRODUCT_SQL = """
                TRUNCATE TABLE product CASCADE;                           
                """;
    String DELETE_CATEGORY_SQL = """
                TRUNCATE TABLE category CASCADE;                           
                """;
    String DELETE_ORDERS_SQL = """
                TRUNCATE TABLE orders CASCADE;                            
                """;
    String DELETE_POSITION_SQL = """
                TRUNCATE TABLE position CASCADE;                             
                """;
    String DELETE_USER_POSITION_SQL = """
                TRUNCATE TABLE user_position CASCADE;                          
                """;
}
