package com.yevhent.microservicedemo.repository;

import com.yevhent.microservicedemo.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface TourRepository extends CrudRepository<Tour, String>, PagingAndSortingRepository<Tour, String> {

    Page<Tour> findByTourPackageCode(String code, Pageable pageable);

    /**
     * Only return the main fields of a Tour, not the details
     *
     * @param code tour package code
     * @return tours without details
     */
    @Query(value = "{'tourPackageCode' : ?0 }",
            fields = "{ 'id':1, 'title':1, 'tourPackageCode':1, 'tourPackageName':1}")
    Page<Tour> findSummaryByTourPackageCode(@Param("code") String code, Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(String id);

    @Override
    @RestResource(exported = false)
    void delete(Tour entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}