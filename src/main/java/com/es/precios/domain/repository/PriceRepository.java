package com.es.precios.domain.repository;


import com.es.precios.domain.Prices;

import java.sql.Timestamp;
import java.util.List;

public interface PriceRepository {
    List<Prices> getParameters(Timestamp date, Integer brandId, Long productId);
}
