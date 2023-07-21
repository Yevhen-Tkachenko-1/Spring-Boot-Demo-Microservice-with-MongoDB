package com.yevhent.microservicedemo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Classification of Tours.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TourPackage {

    @Id
    private String code;

    @Column
    private String name;
}