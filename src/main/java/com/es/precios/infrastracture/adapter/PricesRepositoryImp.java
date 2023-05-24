package com.es.precios.infrastracture.adapter;

import com.es.precios.domain.repository.PriceRepository;
import com.es.precios.domain.Prices;
import com.es.precios.domain.repository.PricesSpecification;
import com.es.precios.infrastracture.entity.PricesEntity;
import com.es.precios.infrastracture.mapper.PricesMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PricesRepositoryImp implements PriceRepository {

    @Autowired
    private final PricesH2Repository pricesH2Repository;
    @Autowired
    private final PricesMapper pricesMapper;

    @Override
    public List<Prices> getParameters(Timestamp date, Integer brandId, Long productId) {
        PricesSpecification specification = new PricesSpecification(date, brandId, productId);
        return pricesMapper.toPricesList(pricesH2Repository.findAll(specification));
    }
}

