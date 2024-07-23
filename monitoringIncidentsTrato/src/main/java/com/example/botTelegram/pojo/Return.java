package com.example.botTelegram.pojo;

public record Return(
        Data data,
        String error,
        String status)
{}