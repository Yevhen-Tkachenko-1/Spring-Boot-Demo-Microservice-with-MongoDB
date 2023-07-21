package com.yevhent.microservicedemo.loader;

import com.yevhent.microservicedemo.domain.TourPackage;
import com.yevhent.microservicedemo.service.TourPackageService;
import com.yevhent.microservicedemo.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DemoCommandLineRunner {

    @Value("${database.import.filename}")
    private String dbImportFilename;

    private final TourPackageService tourPackageService;
    private final TourService tourService;

    public void initDatabase() {
        try {
            System.out.println();
            System.out.println("Total Tour Packages initially: " + tourPackageService.total());
            createTourAllPackages();
            System.out.println("Total Tour Packages created: " + tourPackageService.total());
            for (TourPackage tourPackage : tourPackageService.lookup()) {
                System.out.println(tourPackage);
            }
            System.out.println();
            System.out.println("Total Tours initially: " + tourService.total());
            createTours(dbImportFilename);
            System.out.println("Total Tours created: " + tourService.total());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initialize all the known tour packages
     */
    private void createTourAllPackages() {
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

    /**
     * Create tour entities from an external file
     */
    private void createTours(String fileToImport) throws IOException {
        TourWrapper.read(fileToImport).forEach(importedTour ->
                tourService.createTour(importedTour.getTitle(),
                        importedTour.getDescription(),
                        importedTour.getPrice(),
                        importedTour.getLength(),
                        importedTour.getBullets(),
                        importedTour.getKeywords(),
                        importedTour.getPackageType(),
                        importedTour.getDifficulty(),
                        importedTour.getRegion()));
    }
}