package com.example.demo.service.book.externallibrary.googlebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {
    private String title;
    private String subtitle;
    private List<String> authors;
    private List<IndustryIdentifier> industryIdentifiers;
    private String language;

    public VolumeInfo() {}
}
