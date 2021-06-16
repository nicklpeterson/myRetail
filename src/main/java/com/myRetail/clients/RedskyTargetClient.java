package com.myRetail.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.dto.RedskyResponseDto;
import com.myRetail.exceptions.HttpRequestException;
import lombok.NoArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@NoArgsConstructor
public class RedskyTargetClient {
    private static final String URL = "http://redsky.target.com/v3/pdp/tcin/";
    private static final String URL_PARAMS = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,"
            + "rating_and_review_statistics,question_answer_statistics&key=recruit";

    public RedskyResponseDto requestProduct(String id) throws HttpRequestException {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            final HttpGet request = new HttpGet(URL + id + URL_PARAMS);
            request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            final CloseableHttpResponse response = httpClient.execute(request);
            final String json = EntityUtils.toString(response.getEntity());

            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, RedskyResponseDto.class);
        } catch (IOException e) {
            throw new HttpRequestException(e.getMessage(), e);
        }
    }

}
