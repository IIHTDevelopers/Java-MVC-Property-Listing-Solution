package com.yaksha.training.property.repository;

import com.yaksha.training.property.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query(value = "Select p from Property p "
            + "where (:name IS NULL OR lower(p.name) like %:name%) "
            + "AND (:max IS NULL OR p.price <= :max) ")
    Page<Property> findByPropertyNameAndMaxPrice(@Param("name") String name,
                                                 @Param("max") Double max,
                                                 Pageable pageable);

    Page<Property> findAll(Pageable pageable);
}
