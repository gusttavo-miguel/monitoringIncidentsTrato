package com.example.botTelegram.newPojo;

import java.util.List;

public record Data(
        List<Ticket> items,
        int totalCount)
{}