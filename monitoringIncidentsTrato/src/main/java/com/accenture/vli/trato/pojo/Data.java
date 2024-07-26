package com.accenture.vli.trato.pojo;

import java.util.List;

public record Data(
        List<Ticket> items,
        int totalCount)
{}