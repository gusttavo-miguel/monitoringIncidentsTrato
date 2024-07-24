package com.example.botTelegram.utis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RemoveJsonObject {

    public String removeJsonObject(String json){

        // Analisa a string JSON
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonObject("data").getAsJsonArray("items");

        // Cria um novo JsonArray para armazenar itens que não estão com a “Phase” igual a “Em atendimento”
        JsonArray filteredItemsArray = new JsonArray();

        // Itera sobre os itens que não estão com a “Phase” igual a “Em atendimento” ao novo array
        for (JsonElement item : itemsArray) {
            JsonObject itemObject = item.getAsJsonObject();
            if (!itemObject.has("Phase") && !itemObject.get("Phase").getAsString().equals("Em atendimento")) {
                filteredItemsArray.add(itemObject);
            }
        }

        // Substitue a matriz de itens original pela filtrada
        jsonObject.getAsJsonObject("data").add("items", filteredItemsArray);

        jsonObject.getAsJsonObject(null);

        return jsonObject.toString();
    }
}