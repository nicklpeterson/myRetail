package com.myRetail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
Test Case 1: Get Products/{id} returns product information correctly when product and price information are present

Test Case 2: Get Products/{id} returns product information correctly when product is present and price is not

Test Case 3: Get Products/{id} returns 404 when product cannot be found

Test Case 4: Get Products/{id} returns 404 when only price data is found
 */

@SpringBootTest
public class MyRetailServerTests {
    @Test
    public void contextLoads() throws Exception {
    }
}
