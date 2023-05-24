package com.es.precios.domain.service;

import com.es.precios.application.response.PriceResponse;

import java.time.LocalDateTime;

public interface IPriceService {
    PriceResponse searchPrice(LocalDateTime date, Integer brandId, Long productId);
}
