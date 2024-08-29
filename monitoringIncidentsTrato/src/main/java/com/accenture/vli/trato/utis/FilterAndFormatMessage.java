package com.accenture.vli.trato.utis;

import com.accenture.vli.trato.pojo.Return;
import com.google.gson.Gson;

public class FilterAndFormatMessage {

    public String filterAndFormatMessage(String json) {
        var returnData = new Gson().fromJson(json, Return.class);

        var data = returnData.data();
        if (data == null) {
            return "Teste";
        }

        var messageBuilder = new StringBuilder();
        var responseReceivedCount = data.items().stream().filter(item -> item.ReasonCode().equalsIgnoreCase("Resposta recebida")).count();

        if(responseReceivedCount != 0){
            messageBuilder.append("⚠️ ")
                    .append(responseReceivedCount > 1 ? "Respostas recebidas!" : "Resposta recebida!")
                    .append("\n");
        }

        data.items().stream()
                .filter(item -> item.ReasonCode().equalsIgnoreCase("Resposta recebida"))
                .forEach(item -> {

                    messageBuilder.append("\n")
                            .append("Número do chamado: ").append(item.TicketIdentifier()).append("\n")
                            .append("Solicitante: ").append(item.CreationUserName()).append("\n")
                            .append("Descrição: ").append(item.Description()).append("\n\n")
                            .append("Último Histórico de atividade: ").append(item.LastWorklog())
                            .append("\n_______________________________\n");

                });

        var incidentCount = data.items().stream().filter(item -> item.ReasonCode().equalsIgnoreCase("Atribuído ao grupo") | item.ReasonCode().equalsIgnoreCase("Novo chamado")).count();

        if(incidentCount != 0){
            messageBuilder.append("⚠️ ")
                    .append(incidentCount > 1 ? "Novos chamados identificados!" : "Novo chamado identificado!")
                    .append("\n");
        }

        Dicionary dicionary = new Dicionary();
        data.items().stream()
                .filter(item -> item.ReasonCode().equalsIgnoreCase("Atribuído ao grupo") | item.ReasonCode().equalsIgnoreCase("Novo chamado"))
                .forEach(item -> {

                    messageBuilder.append("\n")
                            .append("Número do chamado: ").append(item.TicketIdentifier()).append("\n")
                            .append("Status: ").append(dicionary.translateToPortuguese(item.TicketStatus())).append("\n")
                            .append("Solicitante: ").append(item.CreationUserName()).append("\n")
                            .append("Descrição: ").append(item.Description())
                            .append("\n_______________________________\n");
                });
        return messageBuilder.toString();
    }
}