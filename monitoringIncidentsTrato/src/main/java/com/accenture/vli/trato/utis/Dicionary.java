package com.accenture.vli.trato.utis;

import java.util.HashMap;
import java.util.Map;

public class Dicionary {

    private Map<String, String> dicionary;

    public String translateToPortuguese(String word) {
        dicionary = new HashMap<>();
        dicionary.put("Active", "Ativo");
        dicionary.put("New", "Novo");
        dicionary.put("Queued", "Na fila");
        return dicionary.get(word);
    }
}