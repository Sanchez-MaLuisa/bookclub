package com.example.demo.service.book.externallibrary.googlebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private VolumeInfo volumeInfo;

    public Item() {}
}
