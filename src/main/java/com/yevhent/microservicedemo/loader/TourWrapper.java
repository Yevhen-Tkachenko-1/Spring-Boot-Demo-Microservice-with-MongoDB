package com.yevhent.microservicedemo.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Getter
public class TourWrapper {

    //fields
    String title;
    String packageName;
    Map<String, String> details;

    public TourWrapper(Map<String, String> record) {
        this.title = record.get("title");
        this.packageName = record.get("packageType");
        this.details = record;
        this.details.remove("packageType");
        this.details.remove("title");
    }

    //reader
    static List<TourWrapper> read(String fileToImport) throws IOException {
        List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY)
                .readValue(new FileInputStream(fileToImport), new TypeReference<>() {
                });
        return records.stream().map(TourWrapper::new)
                .collect(Collectors.toList());
    }
}