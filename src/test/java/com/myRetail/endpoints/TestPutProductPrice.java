package com.myRetail.endpoints;

import com.myRetail.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static com.myRetail.TestingConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestPutProductPrice extends AbstractTest {
    /*
    TEST CASE 1

    PUT /products/price/{id} responds with a 404 forbidden when the Authorization header is missing from the request
     */
    @Test
    public void putProductPriceNoAuthHeader() throws Exception {
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + BIG_LEBOWSKI_ID)
                .content("{\"price\":1000, \"currency\":\"CAD\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(status().isUnauthorized());
    }

    /*
    TEST CASE 2

    PUT /products/price/{id} responds with a 404 forbidden when the Authorization header was not issued by our authentication layer
     */
    @Test
    public void putProductPriceBadAuthHeader() throws Exception {
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + BIG_LEBOWSKI_ID)
                .content("{\"price\":1000, \"currency\":\"CAD\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization", FAKE_JWT))
                .andExpect(status().isUnauthorized());
    }

    /*
    TEST CASE 3

    PUT /products/price/{id} updates the price in the database and returns the updated product when
    it is found in the database and by the redsky api
    */
    @Test
    public void putProductPriceProductFound() throws Exception {
        final String JWT = postLogin();
        final ProductDto productDto = PRODUCT_DTO_MAP.get(BIG_LEBOWSKI_ID);
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + BIG_LEBOWSKI_ID)
                .content("{\"price\":1000, \"currency\":\"CAD\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization", JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.id").value(BIG_LEBOWSKI_ID))
                .andExpect(jsonPath("$.currentPrice.currency").value("CAD"))
                .andExpect(jsonPath("$.currentPrice.price").value(1000));

        // Test that the price information has been updated
        this.mockMvc.perform(get(GET_PRODUCTS_URI + BIG_LEBOWSKI_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.currentPrice.currency").value("CAD"))
                .andExpect(jsonPath("$.currentPrice.price").value(1000));
    }

    /*
    TEST CASE 4

    PUT /products/price/{id} adds the product price to the database when it is not in the database,
    but is found by the redsky api and returns the updated product
     */
    @Test
    public void putProductsPriceNotInDatabase() throws Exception {
        // confirm that kraft mac has no price data
        final ProductDto productDto = PRODUCT_DTO_MAP.get(KRAFT_MACARONI_ID);
        this.mockMvc.perform(get(GET_PRODUCTS_URI + KRAFT_MACARONI_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.id").value(productDto.getId()))
                .andExpect(jsonPath("$.*", hasSize(2)));

        // send put request
        final String JWT = postLogin();
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + KRAFT_MACARONI_ID)
                .content("{\"price\":1000, \"currency\":\"CAD\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization", JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.id").value(KRAFT_MACARONI_ID))
                .andExpect(jsonPath("$.currentPrice.currency").value("CAD"))
                .andExpect(jsonPath("$.currentPrice.price").value(1000));

        // Test that the price information has been updated
        this.mockMvc.perform(get(GET_PRODUCTS_URI + KRAFT_MACARONI_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.currentPrice.currency").value("CAD"))
                .andExpect(jsonPath("$.currentPrice.price").value(1000));
    }

    /*
    TEST CASE 5

    PUT /products/price/{id} returns a 404 status when the product is not found by the redsky api and is not found in the database
     */
    @Test
    public void putProductPriceNotFound() throws Exception {
        final String JWT = postLogin();
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + "1")
                .content("{\"price\":1000, \"currency\":\"CAD\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization", JWT))
                .andExpect(status().isNotFound());
    }

    /*
    TEST CASE 6

    PUT /products/price/{id} returns a 404 status when the product is not found by the redsky api and is found in the database
     */
    @Test
    public void putProductPriceNotFoundInRedskyApiButIsPresentInDB() throws Exception {
        final String JWT = postLogin();
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + "2")
                .content("{\"price\":1000, \"currency\":\"CAD\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization", JWT))
                .andExpect(status().isNotFound());
    }

    /*
    TEST CASE 7

    PUT /products/price/{id} returns a 400 status when currency code is invalid
     */
    @Test
    public void putProductPriceInvalidCurrencyCode() throws Exception {
        final String JWT = postLogin();
        this.mockMvc.perform(put(PUT_PRODUCTS_PRICE_URI + BIG_LEBOWSKI_ID)
                .content("{\"price\":1000, \"currency\":\"NotARealCurrency\"}")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization", JWT))
                .andExpect(status().isBadRequest());
    }

    /*
    A helper method to obtain an authenticated JSON Web Token
     */
    private String postLogin() throws Exception {
        return this.mockMvc.perform(post(LOGIN_URI)
                .content("{\"username\": \"admin\", \"password\": \"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
    }
}
