package com.example.botTelegram.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Return {
    private com.example.botTelegram.pojo.Data data;
    private String error;
    private String status;
}