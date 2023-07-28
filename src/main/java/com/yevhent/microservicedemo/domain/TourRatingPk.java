package com.yevhent.microservicedemo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Tour Rating Primary Key containing a Tour and a Customer Identifier
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TourRatingPk implements Serializable {

    @ManyToOne
    private Tour tour;

    @Column(insertable = false, updatable = false, nullable = false)
    private Integer customerId;

    @Override
    public String toString() {
        return "TourRatingPk{" +
                "tourId=" + tour.getId() +
                ", customerId=" + customerId +
                '}';
    }
}
