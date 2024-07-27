package com.accenture.vli.trato.service;

import com.accenture.vli.trato.pojo.Return;
import com.accenture.vli.trato.utis.RemoveJsonObject;
import com.accenture.vli.trato.utis.serviceaideData.Credentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ServiceaideService {

    public String getIncidents() throws JsonProcessingException {
        var restTemplate = new RestTemplate();
        var credentials = new Credentials();

        var headers = new HttpHeaders();
        headers.set("csm_app_url", credentials.getCSM_APP_URL());
        headers.set("user_auth_token", credentials.getUSER_AUTH_TOKEN());
        headers.set("slice_token", credentials.getSLICE_TOKEN());
        headers.set("webservice_user_name", credentials.getWEBSERVICE_USER_NAME());
        headers.set("webservice_user_password", credentials.getWEBSERVICE_USER_PASSWORD());

        var entity = new HttpEntity<String>(headers);

        var apiUrl = credentials.getAPI_URL();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        var json = response.getBody();

        var removeJsonObject = new RemoveJsonObject();
        var newJson = removeJsonObject.removeJsonObject(json);

        var returnData = new Gson().fromJson(newJson, Return.class);

        var items = returnData.data().items();
        if (items.isEmpty()) {
            return "";
        }

        var messageBuilder = new StringBuilder();

        var responseReceived = items.stream().filter(item -> item.ReasonCode().equals("Resposta recebida"));
        var responseReceivedCount = responseReceived.count();

        items.stream()
                .filter(item -> item.ReasonCode().equals("Resposta recebida"))
                .forEach(item -> {
                    messageBuilder.append("⚠️ ")
                            .append(responseReceivedCount > 1 ? "Respostas recebidas" : "Resposta recebida")
                            .append("\n");

                    messageBuilder.append("\n")
                            .append("Número do chamado: ").append(item.TicketIdentifier()).append("\n")
                            .append("Solicitante: ").append(item.CreationUserName()).append("\n")
                            .append("Descrição: ").append(item.Description()).append("\n\n")
                            .append("Último Histórico de atividade: ").append(item.LastWorklog())
                            .append("\n_______________________________\n");

                });

        var incidentCount = items.stream().filter(item -> item != null).count();
        items.stream()
                .filter(item -> !item.ReasonCode().equals("Resposta recebida"))
                .forEach(item -> {
                    messageBuilder.append("⚠️ ")
                            .append(incidentCount > 1 ? "Novos chamados identificados!" : "Novo chamado identificado!")
                            .append("\n");

                    messageBuilder.append("\n")
                            .append("Número do chamado: ").append(item.TicketIdentifier()).append("\n")
                            .append("Status: ").append(item.TicketStatus()).append("\n")
                            .append("Solicitante: ").append(item.CreationUserName()).append("\n")
                            .append("Descrição: ").append(item.Description())
                            .append("\n_______________________________\n");
                });
        return messageBuilder.toString();
    }
}