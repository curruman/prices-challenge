package com.es.precios.infrastracture.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricesEntity {

    @Column(name = "price_list_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long priceListId;

    @Column(name = "brand_id")
    private int brandId;

    @Column(name = "currency_code")
    private String currencyCode;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH.mm.ss")
    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "priority")
    private int priority;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 9, fraction = 2)
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_id")
    private Long productId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH.mm.ss")
    @Column(name = "start_date")
    private Timestamp startDate;
}
