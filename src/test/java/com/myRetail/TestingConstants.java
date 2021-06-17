package com.myRetail;

import com.myRetail.dto.ProductDto;

import java.util.HashMap;
import java.util.Map;

public class TestingConstants {
    public static final String GET_PRODUCTS_URI = "/products/";
    public static final String PUT_PRODUCTS_PRICE_URI = "/products/price/";
    public static final String LOGIN_URI = "/login";

    public static final String BIG_LEBOWSKI_ID = "13860428";
    public static final String CREAMY_PEANUT_BUTTER_ID = "54456119";
    public static final String JIF_PEANUT_BUTTER_ID = "13264003";
    public static final String KRAFT_MACARONI_ID = "12954218";

    public static final ProductDto BIG_LEBOWSKI_PRODUCT_DTO =
            new ProductDto(BIG_LEBOWSKI_ID, "The Big Lebowski (Blu-ray)", "USD", 13.49);
    public static final ProductDto CREAMY_PEANUT_BUTTER_PRODUCT_DTO =
            new ProductDto(CREAMY_PEANUT_BUTTER_ID, "Creamy Peanut Butter 40oz - Good &#38; Gather&#8482;", "USD", 3.59);
    public static final ProductDto JIF_PEANUT_BUTTER_PRODUCT_DTO =
            new ProductDto(JIF_PEANUT_BUTTER_ID, "Jif Natural Creamy Peanut Butter - 40oz", "CAD", 4.99);
    public static final ProductDto KRAFT_MACARONI_PRODUCT_DTO =
            new ProductDto(KRAFT_MACARONI_ID, "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz");

    public static final Map<String, ProductDto> PRODUCT_DTO_MAP;
    static {
        PRODUCT_DTO_MAP = new HashMap<>();
        PRODUCT_DTO_MAP.put(BIG_LEBOWSKI_ID, BIG_LEBOWSKI_PRODUCT_DTO);
        PRODUCT_DTO_MAP.put(CREAMY_PEANUT_BUTTER_ID, CREAMY_PEANUT_BUTTER_PRODUCT_DTO);
        PRODUCT_DTO_MAP.put(JIF_PEANUT_BUTTER_ID, JIF_PEANUT_BUTTER_PRODUCT_DTO);
        PRODUCT_DTO_MAP.put(KRAFT_MACARONI_ID, KRAFT_MACARONI_PRODUCT_DTO);
    }

    public static final String FAKE_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImp0aSI6IjgxM2Q0NjhmLTExNzItNDIxZi1hZjc0LTViOTEyMWMyNmU5MiIsImlhdCI6MTYyMzk0NTEzNywiZXhwIjoxNjIzOTQ4NzM3fQ.FkWMA2WP25Lpwa7TI0kLYfYvBBs2KVtRwE2pAc6QimM";

}
