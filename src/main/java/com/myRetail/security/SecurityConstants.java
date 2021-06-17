package com.myRetail.security;

public class SecurityConstants {
    public static final String SECRET = "redskyMyRetailSuperSecretKey";
    public static final long EXPIRATION_TIME = 900000; // 15 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ACCESS_CONTROL_HEADER = "Access-Control-Expose-Headers";
}
