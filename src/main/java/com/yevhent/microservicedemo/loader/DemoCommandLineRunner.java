package com.yevhent.microservicedemo.loader;

import com.yevhent.microservicedemo.domain.Tour;
import com.yevhent.microservicedemo.domain.TourPackage;
import com.yevhent.microservicedemo.domain.TourRating;
import com.yevhent.microservicedemo.repository.TourRatingRepository;
import com.yevhent.microservicedemo.service.TourPackageService;
import com.yevhent.microservicedemo.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DemoCommandLineRunner {

    @Value("${database.import.filename}")
    private String dbImportFilename;

    private final TourPackageService tourPackageService;
    private final TourService tourService;
    private final TourRatingRepository tourRatingRepository;

    public void initDatabase() {
        try {
            System.out.println();
            System.out.println("Total Tour Packages initially: " + tourPackageService.total());
            initTourPackages();
            System.out.println("Total Tour Packages created: " + tourPackageService.total());
            for (TourPackage tourPackage : tourPackageService.lookup()) {
                System.out.println(tourPackage);
            }
            System.out.println();
            System.out.println("Total Tours initially: " + tourService.total());
            List<Tour> tours = initTours(dbImportFilename);
            System.out.println("Total Tours created: " + tourService.total());
            for (Tour tour : tourService.getAll()) {
                System.out.println(tour);
            }
            System.out.println();
            System.out.println("Total Tour Ratings initially: " + tourRatingRepository.count());
            initTourRatings(tours);
            System.out.println("Total Tour Ratings created: " + tourRatingRepository.count());
            for (TourRating tourRating : tourRatingRepository.findAll()) {
                System.out.println(tourRating);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTourPackages() {
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }

    private List<Tour> initTours(String fileToImport) throws IOException {
        return TourWrapper.read(fileToImport).stream()
                .map(importedTour ->
                        tourService.createTour(
                                importedTour.getTitle(),
                                importedTour.getDescription(),
                                importedTour.getPrice(),
                                importedTour.getLength(),
                                importedTour.getBullets(),
                                importedTour.getKeywords(),
                                importedTour.getPackageType(),
                                importedTour.getDifficulty(),
                                importedTour.getRegion())
                )
                .collect(Collectors.toList());
    }

    private void initTourRatings(List<Tour> tours) {

        tours.stream().limit(3).forEach(tour -> {
            for (int i = 3; i <= 5; i++) {
                tourRatingRepository.save(new TourRating(tour, i * 10, i));
            }
        });
    }
}