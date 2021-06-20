package com.myRetail.endpoints;

import com.myRetail.dto.ProductDto;
import com.myRetail.models.Price;
import com.myRetail.repositories.PriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.myRetail.TestingConstants.KRAFT_MACARONI_ID;
import static com.myRetail.TestingConstants.PRODUCT_DTO_MAP;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@AutoConfigureMockMvc
public abstract class AbstractTest {

    @Autowired
    protected PriceRepository priceRepository;

    @Autowired
    protected MockMvc mockMvc;

    /**
     * Set up the database as needed by tests
     */
    @BeforeEach
    public void initDB() {
        assertThat(priceRepository).isNotNull();
        for (String id : PRODUCT_DTO_MAP.keySet()) {
            ProductDto productDto = PRODUCT_DTO_MAP.get(id);
            if (productDto.getCurrentPrice() != null) {
                priceRepository.save(
                        Price.builder()
                                .currency(productDto.getCurrentPrice().getCurrency())
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

    /**
     * reset the state of the database to the initial state
     */
    @AfterEach
    public void resetDB() {
        for (String id : PRODUCT_DTO_MAP.keySet()) {
            ProductDto productDto = PRODUCT_DTO_MAP.get(id);
            if (productDto.getCurrentPrice() != null) {
                priceRepository.save(
                        Price.builder()
                                .currency(productDto.getCurrentPrice().getCurrency())
                                .price(productDto.getCurrentPrice().getPrice())
                                .productId(id)
                                .build()
                );
            }
        }

        priceRepository.deleteById("2");
        priceRepository.save(
                Price.builder()
                .productId(KRAFT_MACARONI_ID)
                .currency("CAD")
                .price(17.55)
                .build()
        );
    }

    /**
     * Smoke Test to ensure the autowired MockMvc has loaded correctly
     */
    @Test
    public void contextLoads() throws Exception {
        assertThat(mockMvc).isNotNull();
    }
}
