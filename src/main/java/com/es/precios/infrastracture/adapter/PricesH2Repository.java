package com.es.precios.infrastracture.adapter;

import com.es.precios.infrastracture.entity.PricesEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface PricesH2Repository extends JpaRepository<PricesEntity, Serializable>,
        JpaSpecificationExecutor<PricesEntity> {
   List<PricesEntity> findAll(Specification<PricesEntity> specification);
}
