package com.es.precios.application.rest;

import com.es.precios.PriceApplication;
import com.es.precios.application.response.PriceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PriceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PricesControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private static WireMockServer wiremock;

    private static final String PARTIAL_PATH = "/prices/api/v1/searchPrice/";

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @BeforeAll
    static void setup() {
        wiremock = new WireMockServer(8096);
    }

    public static Stream<Arguments> parameterByTest() {
        return Stream.of(
                Arguments.of(1, "2020-06-14-10.00.00", new BigDecimal("35.5"), 35455L),
                Arguments.of(1, "2020-06-14-16.00.00", new BigDecimal("25.45"), 35455L),
                Arguments.of(1, "2020-06-14-21.00.00", new BigDecimal("35.5"), 35455L),
                Arguments.of(1, "2020-06-15-10.00.00", new BigDecimal("30.5"), 35455L),
                Arguments.of(1, "2020-06-16-21.00.00", new BigDecimal("38.95"), 35455L)
        );
    }

    @ParameterizedTest
    @MethodSource("parameterByTest")
    void givenPriceInControllerWithoutErrors(Integer brandId, String startDate, BigDecimal price,
                                             Long productId) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);

        PriceResponse priceResponse = PriceResponse.builder()
                .productId(productId)
                .brandId(brandId)
                .price(price)
                .dateToFound(dateTime)
                .finalPrice(price.multiply(BigDecimal.valueOf(0.21)))
                .build();

        this.mockMvc
                .perform(
                        get(PARTIAL_PATH + brandId + "/" + productId + "?date=" + startDate)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(priceResponse)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value(price));
    }

    @Test
    void givenPriceThenInputErrorRestControllerAdvice() throws Exception {
        Integer brandId = 1;
        Long productId = 35455L;
        String dateString = "XXXX";

        PriceResponse priceResponseIncomplete = PriceResponse.builder()
                .productId(productId)
                .brandId(brandId)
                .dateToFound(null)
                .build();

        wiremock.stubFor(stubExceptionParameters(brandId, productId, dateString));

        this.mockMvc
                .perform(
                        get(PARTIAL_PATH + brandId + "/" + productId + "?date=" + dateString)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(priceResponseIncomplete)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    private MappingBuilder stubExceptionParameters(Integer brandId, Long productId, String date){
        String path = PARTIAL_PATH + brandId + "/" + productId + "?date=" + date;
        return WireMock.get(urlPathMatching(path))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse().withStatus(500));
    }
}