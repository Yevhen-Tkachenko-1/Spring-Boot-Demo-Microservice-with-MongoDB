package com.yevhent.microservicedemo.web;

import com.yevhent.microservicedemo.domain.TourRating;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourRatingDto {

    private Integer score;

    private String comment;

    @Nonnull
    private Integer customerId;

    public TourRatingDto(TourRating tourRating) {
        this(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
    }
}
