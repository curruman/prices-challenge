package com.es.precios.application.rest;

import com.es.precios.application.response.PriceResponse;
import com.es.precios.domain.service.IPriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.es.precios.application.utils.ConstantsPrices.*;


@RestController
@RequestMapping("/prices/api/v1")
@AllArgsConstructor
@Slf4j
public class PricesController {

    @Autowired
    private final IPriceService priceService;

    @GetMapping(path = "/searchPrice/" + BRAND_ID + PATH_SEPARATOR + PRODUCT_ID,
            consumes= MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceResponse> searchPrice(@RequestParam(name = "date", required = true) LocalDateTime date,
                                                     @PathVariable Long productId, @PathVariable Integer brandId) {

        log.info("Find price valid.");
        return ResponseEntity.ok(priceService.searchPrice(date, brandId, productId));
    }

}
