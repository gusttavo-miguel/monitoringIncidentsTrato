package com.example.botTelegram.pojo;

import java.util.List;

public record Data(
        List<Ticket> items,
        int totalCount)
{}