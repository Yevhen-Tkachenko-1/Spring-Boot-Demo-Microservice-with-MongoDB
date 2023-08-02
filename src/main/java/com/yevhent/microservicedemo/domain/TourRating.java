package com.yevhent.microservicedemo.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Rating of a Tour by a Customer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TourRating {

    @Id
    private String id;

    private String tourId;

    @NotNull
    private Integer customerId;

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    public TourRating(String tourId, @NotNull Integer customerId, Integer score) {
        this.tourId = tourId;
        this.customerId = customerId;
        this.score = score;
        this.comment = toString(score);
    }

    public static String toString(Integer score) {
        switch (score) {
            case 1:
                return "worst";
            case 2:
                return "worse";
            case 3:
                return "well";
            case 4:
                return "better";
            case 5:
                return "best";
            default:
                return "Unknown score: " + score;
        }
    }
}
