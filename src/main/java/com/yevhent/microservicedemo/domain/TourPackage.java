package com.yevhent.microservicedemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Classification of Tours.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TourPackage {

    @Id
    private String code;

    private String name;
}