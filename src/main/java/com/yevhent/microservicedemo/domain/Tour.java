package com.yevhent.microservicedemo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @NonNull
    private String title;

    @Column(length = 2000)
    @NonNull
    private String description;

    @Column
    @NonNull
    private Integer price;

    @Column
    @NonNull
    private String duration;

    @Column(length = 2000)
    @NonNull
    private String bullets;

    @Column
    @NonNull
    private String keywords;

    @ManyToOne
    @NonNull
    private TourPackage tourPackage;

    @Column
    @Enumerated
    @NonNull
    private Difficulty difficulty;

    @Column
    @Enumerated
    @NonNull
    private Region region;

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", duration='" + duration + '\'' +
                ", tourPackage=" + tourPackage +
                ", difficulty=" + difficulty +
                ", region=" + region +
                '}';
    }
}