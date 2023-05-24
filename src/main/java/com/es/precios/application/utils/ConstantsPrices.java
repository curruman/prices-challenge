package com.es.precios.application.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConstantsPrices {

   /*Controller's paths*/
    public static final String PATH_SEPARATOR="/";
    public static final String PRODUCT_ID="{productId}";
    public static final String BRAND_ID="{brandId}";
}
