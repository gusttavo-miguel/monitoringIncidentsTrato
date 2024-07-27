//package com.accenture.vli.trato.utis;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//public class RemoveJsonObject {
//
//    public String removeJsonObject(String json){
//
//        // Analisa a string JSON
//        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//        JsonArray itemsArray = jsonObject.getAsJsonObject("data").getAsJsonArray("items");
//
//        // Cria um novo JsonArray para armazenar itens que não estão com o “ReasonCode” igual a “Chamado assumido”
//        JsonArray filteredItemsArray = new JsonArray();
//
//        // Itera sobre os itens que não estão com o “ReasonCode” igual a “Chamado assumido” ao novo array
//        for (JsonElement item : itemsArray) {
//            JsonObject itemObject = item.getAsJsonObject();
//            if (!itemObject.get("ReasonCode").getAsString().contentEquals("Chamado assumido")) {
//                filteredItemsArray.add(itemObject);
//            }
//        }
//
//        // Substitue a matriz de itens original pela filtrada
//        jsonObject.getAsJsonObject("data").add("items", filteredItemsArray);
//
//        return jsonObject.toString();
//    }
//}