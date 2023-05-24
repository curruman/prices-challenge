package com.es.precios.domain.service;

import com.es.precios.application.response.PriceResponse;
import com.es.precios.domain.exception.DateException;
import com.es.precios.domain.exception.ParametersException;
import com.es.precios.domain.repository.PriceRepository;
import com.es.precios.domain.Prices;
import com.es.precios.infrastracture.configuration.SelfConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
@Slf4j
@Service
public class PriceService implements IPriceService {

    @Autowired
    private final PriceRepository priceRepository;
    @Autowired
    private final SelfConfiguration selfConfiguration;

    @Override
    public PriceResponse searchPrice(LocalDateTime date, Integer brandId, Long productId) {
        log.info("Price in the date: "+date+"with the brand: "+brandId +"of productId: "+productId);

        if (isNull(date) || isNull(productId) || isNull(brandId)) {
            throw new ParametersException("Cannot be null parameters");
        }

        Timestamp timeStampH2= Optional.of(Timestamp.valueOf(date))
                .orElseThrow(()-> new DateException("Invalidate date."));

        Prices priceSelected= ofNullable(priceRepository.getParameters(timeStampH2, brandId, productId))
                .map(prices -> prices.get(0)).orElse(null);

        BigDecimal priceToApply = priceSelected.getPrice().add(
                priceSelected.getPrice().multiply(BigDecimal.valueOf(selfConfiguration.getTax())))
                .setScale(2, RoundingMode.HALF_UP);

        return PriceResponse.builder()
                .productId(priceSelected.getProductId())
                .brandId(priceSelected.getBrandId())
                .price(priceSelected.getPrice())
                .dateToFound(date)
                .finalPrice(priceToApply)
                .build();
    }

}
