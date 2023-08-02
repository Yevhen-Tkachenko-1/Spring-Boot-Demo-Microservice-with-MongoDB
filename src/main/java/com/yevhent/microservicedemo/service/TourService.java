package com.yevhent.microservicedemo.service;

import com.yevhent.microservicedemo.domain.Tour;
import com.yevhent.microservicedemo.domain.TourPackage;
import com.yevhent.microservicedemo.repository.TourPackageRepository;
import com.yevhent.microservicedemo.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
                new RuntimeException("Tour package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    public long total() {
        return tourRepository.count();
    }

    public List<Tour> getAll() {
        return (List<Tour>) tourRepository.findAll();
    }
}