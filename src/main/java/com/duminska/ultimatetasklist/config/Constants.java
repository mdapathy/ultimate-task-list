package com.duminska.ultimatetasklist.config;

public class Constants {
    public static final String USER_URLS = "/api/user";
    public static final String TASK_URLS = "/api/tasks";
    public static final String PROJECT_URLS = "/api/project";

    public static final String SECURE_USER_URLS = "/api/user/**";

    public static final long EXPIRATION_TIME = 86_400_000; // 24 hours
    public static final String ACTIVATION_URL = "https://ultimate-tasklist.herokuapp.com/activate?key=";
    public static final String SECRET = "secretforultimatetasklist";
}
