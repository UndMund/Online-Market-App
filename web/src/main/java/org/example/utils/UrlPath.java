package org.example.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String MAIN = "/";
    public static final String USER_PROFILE = "/user_menu/";
    public static final String ADMIN_PROFILE = "/admin_menu/";
    public static final String LOGOUT = "/logout";
    public static final String ABOUT = USER_PROFILE + "about";
    public static final String CHANGE_PASSWORD = USER_PROFILE + "change_password";
    public static final String MY_AD = USER_PROFILE + "advertisements";
    public static final String NEW_AD = USER_PROFILE + "add_advertisement";
    public static final String REPLENISH_BALANCE = USER_PROFILE + "replenish";
    public static final String ALL_USERS = ADMIN_PROFILE + "users";
    public static final String VERIFY_AD = ADMIN_PROFILE + "verify";
    public static final String ALL_ORDERS = ADMIN_PROFILE + "orders";
}
