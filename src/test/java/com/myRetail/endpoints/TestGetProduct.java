package com.myRetail.endpoints;

import com.myRetail.controller.ProductController;
import com.myRetail.dto.ProductDto;
import com.myRetail.models.Price;
import com.myRetail.repositories.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.myRetail.TestingConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class TestGetProduct {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void resetDB() {
        assertThat(priceRepository).isNotNull();
        for (String id : PRODUCT_DTO_MAP.keySet()) {
            ProductDto productDto = PRODUCT_DTO_MAP.get(id);
            if (productDto.getCurrentPrice() != null) {
                priceRepository.save(
                        Price.builder()
                                .currency(productDto.getCurrentPrice().getCurrencyCode())
                                .price(productDto.getCurrentPrice().getPrice())
                                .productId(id)
                                .build()
                );
            }
        }

        // Make sure the Kraft Macaroni is not in the DB, so we can test cases
        // when an item is found in the redsky api, but not the database.
        priceRepository.deleteById(KRAFT_MACARONI_ID);

        // Add an item that is not found by the redsky api
        priceRepository.save(
                Price.builder()
                        .productId("2")
                        .currency("EUR")
                        .price(1.00)
                        .build()
        );

    }

    /*
    Smoke Test to ensure the autowired MockMvc has loaded correctly
     */
    @Test
    public void contextLoads() throws Exception {
        assertThat(mockMvc).isNotNull();
    }

    /*
    TEST CASE 1

    Get Products/{id} returns product information correctly when product and price information are present
     */
    @Test
    public void getProductWhenAllInformationIsPresent() throws Exception {
        for (String id : PRODUCT_DTO_MAP.keySet()) {
            ProductDto productDto = PRODUCT_DTO_MAP.get(id);
            if (productDto.getCurrentPrice() != null) {
                this.mockMvc.perform(get(GET_PRODUCTS_URI + id))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(productDto.getName()))
                        .andExpect(jsonPath("$.id").value(id))
                        .andExpect(jsonPath("$.currentPrice.currencyCode").value(productDto.getCurrentPrice().getCurrencyCode()))
                        .andExpect(jsonPath("$.currentPrice.price").value(productDto.getCurrentPrice().getPrice()));
            }
        }
    }

    /*
    TEST CASE 2

    Get Products/{id} returns product information correctly when product is present and price is not
     */
    @Test
    public void getProductWhenProductIsNotFoundInDB() throws Exception {
        ProductDto productDto = PRODUCT_DTO_MAP.get(KRAFT_MACARONI_ID);
        this.mockMvc.perform(get(GET_PRODUCTS_URI + KRAFT_MACARONI_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.id").value(productDto.getId()))
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    /*
    TEST CASE 3

    Get Products/{id} returns 404 when product cannot be found
     */
    @Test
    public void getProductNotFound() throws Exception {
        this.mockMvc.perform(get(GET_PRODUCTS_URI + "1"))
                .andExpect(status().is4xxClientError());
    }

    /*
    TEST CASE 4

    Get Products/{id} returns 404 when only price data is found
     */
    @Test
    public void getProductOnlyPriceDataFound() throws Exception {
        this.mockMvc.perform(get(GET_PRODUCTS_URI + "2"))
                .andExpect(status().is4xxClientError());
    }
}
