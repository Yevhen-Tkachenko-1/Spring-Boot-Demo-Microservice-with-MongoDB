package com.yevhent.microservicedemo.repository;

import com.yevhent.microservicedemo.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TourRepository extends CrudRepository<Tour, Integer>, PagingAndSortingRepository<Tour, Integer> {

    Page<Tour> findByTourPackageCode(String code, Pageable pageable);
}