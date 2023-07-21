package com.yevhent.microservicedemo.service;

import com.yevhent.microservicedemo.domain.Difficulty;
import com.yevhent.microservicedemo.domain.Region;
import com.yevhent.microservicedemo.domain.Tour;
import com.yevhent.microservicedemo.domain.TourPackage;
import com.yevhent.microservicedemo.repository.TourPackageRepository;
import com.yevhent.microservicedemo.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public Tour createTour(String title, String description, Integer price,
                           String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
                new RuntimeException("Tour package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(title, description, price, duration,
                bullets, keywords, tourPackage, difficulty, region));
    }

    public long total() {
        return tourRepository.count();
    }
}