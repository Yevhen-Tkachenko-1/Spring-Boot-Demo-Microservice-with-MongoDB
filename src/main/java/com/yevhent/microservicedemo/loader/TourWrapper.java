package com.yevhent.microservicedemo.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevhent.microservicedemo.domain.Difficulty;
import com.yevhent.microservicedemo.domain.Region;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * Helper class to import ExploreCalifornia.json
 */
public class TourWrapper {

    //fields
    private String packageType;
    private String title;
    private String description;
    private String price;
    private String length;
    private String bullets;
    private String keywords;
    private String difficulty;
    private String region;

    //reader
    public static List<TourWrapper> read(String fileToImport) throws IOException {
        return new ObjectMapper().setVisibility(FIELD, ANY).readValue(new FileInputStream(fileToImport), new TypeReference<>() {
        });
    }

    protected TourWrapper() {
    }

    public String getPackageType() {
        return packageType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return Integer.parseInt(price);
    }

    public String getLength() {
        return length;
    }

    public String getBullets() {
        return bullets;
    }

    public String getKeywords() {
        return keywords;
    }

    public Difficulty getDifficulty() {
        return Difficulty.valueOf(difficulty);
    }

    public Region getRegion() {
        return Region.findByLabel(region);
    }
}