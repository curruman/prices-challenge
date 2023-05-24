package com.es.precios.infrastracture.mapper;

import com.es.precios.domain.Prices;
import com.es.precios.infrastracture.entity.PricesEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PricesMapper {
    @Mappings(
            {
                    @Mapping(source = "priceListId", target = "priceListId"),
                    @Mapping(source = "brandId", target = "brandId"),
                    @Mapping(source = "currencyCode", target = "currencyCode"),
                    @Mapping(source = "endDate", target = "endDate"),
                    @Mapping(source = "priority", target = "priority"),
                    @Mapping(source = "price", target = "price"),
                    @Mapping(source = "productId", target = "productId"),
                    @Mapping(source = "startDate", target = "startDate")
            }
    )

    @InheritInverseConfiguration
    List<Prices> toPricesList (List<PricesEntity> pricesEntity);

    default Prices ofPricesEntity(PricesEntity prices){
        return new Prices(prices.getPriceListId(), prices.getBrandId(), prices.getCurrencyCode(),
                prices.getEndDate(), prices.getPriority(), prices.getPrice(), prices.getProductId(),
                prices.getStartDate());
    }
}
