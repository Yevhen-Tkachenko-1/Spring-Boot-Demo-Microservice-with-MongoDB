package com.yevhent.microservicedemo.repository;

import com.yevhent.microservicedemo.domain.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Integer> {

}