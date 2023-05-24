package com.es.precios.infrastracture.configuration;

import com.es.precios.domain.repository.PriceRepository;
import com.es.precios.domain.service.PriceService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.time.format.DateTimeFormatter;

@Configuration
@Data
public class SelfConfiguration implements WebMvcConfigurer {

    @Value("${prices.tax}")
    @Pattern(regexp ="[0,9]+")
    @Digits(integer = 0, fraction =2)
    private double tax;

    @Value("${prices.internal.timeout}")
    private Integer internalTimeout;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));
        registrar.registerFormatters(registry);
    }

}
