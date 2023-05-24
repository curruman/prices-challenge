package com.es.precios.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Prices {
    private Long priceListId;
    private int brandId;
    private String currencyCode;
    private Timestamp endDate;
    private int priority;
    private BigDecimal price;
    private Long productId;
    private Timestamp startDate;
}
