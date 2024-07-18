package com.example.botTelegram.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private List<Item> items;
    private int totalCount;
}