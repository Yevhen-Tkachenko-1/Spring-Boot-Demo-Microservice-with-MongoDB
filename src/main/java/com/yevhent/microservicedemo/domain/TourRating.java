package com.yevhent.microservicedemo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rating of a Tour by a Customer
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourRating {

    @EmbeddedId
    private TourRatingPk pk;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    public static String toString(Integer score) {
        return switch (score) {
            case 1 -> "worst";
            case 2 -> "worse";
            case 3 -> "well";
            case 4 -> "better";
            case 5 -> "best";
            default -> "Unknown score: " + score;
        };
    }

    public TourRating(Tour tour, Integer customerId, Integer score) {
        this.pk = new TourRatingPk(tour, customerId);
        this.score = score;
        this.comment = toString(score);
    }

    public TourRating(Tour tour, Integer customerId, Integer score, String comment) {
        this.pk = new TourRatingPk(tour, customerId);
        this.score = score;
        this.comment = comment;
    }
}
