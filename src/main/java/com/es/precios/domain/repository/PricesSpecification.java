package com.es.precios.domain.repository;

import com.es.precios.infrastracture.entity.PricesEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PricesSpecification implements Specification<PricesEntity> {

    private final Timestamp dateToFound;
    private final Integer brandId;
    private final Long productId;

    @Override
    public Predicate toPredicate(Root<PricesEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.between(criteriaBuilder.literal(dateToFound),
                root.<Timestamp>get("startDate"),
                root.<Timestamp>get("endDate")));
        predicates.add(criteriaBuilder.equal(root.get("brandId"), brandId));
        predicates.add(criteriaBuilder.equal(root.get("productId"), productId));

        query.orderBy(criteriaBuilder.desc(root.get("priority")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}