package com.example.botTelegram.service;

import com.example.botTelegram.pojo.Return;
import com.example.botTelegram.utis.RemoveJsonObject;
import com.example.botTelegram.utis.serviceaideData.Credentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ServiceaidService {

    public String getIncidents() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Credentials credentials = new Credentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("csm_app_url", credentials.getCSM_APP_URL());
        headers.set("user_auth_token", credentials.getUSER_AUTH_TOKEN());
        headers.set("slice_token", credentials.getSLICE_TOKEN());
        headers.set("webservice_user_name", credentials.getWEBSERVICE_USER_NAME());
        headers.set("webservice_user_password", credentials.getWEBSERVICE_USER_PASSWORD());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = credentials.getAPI_URL();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        String json = response.getBody();

        RemoveJsonObject removeJsonObject = new RemoveJsonObject();
        String newJson = removeJsonObject.removeJsonObject(json);

        Gson gson = new Gson();
        Return returnData = gson.fromJson(newJson, Return.class);

        StringBuilder messageBuilder = new StringBuilder();

        if (!returnData.data().items().isEmpty()) {

            messageBuilder.append("\uD83E\uDD16 Incident Monitoring Bot\n");

            int cont = 0;
            for (var item : returnData.data().items()) {
                if (item != null) {
                    cont++;
                }
            }

            messageBuilder.append("⚠️ ").append(cont > 1 ? "Novos chamados identificados!" : "Novo chamado identificado!").append(" ⚠️\n");

            for (var item : returnData.data().items()) {
                String ticketIdentifier = item.TicketIdentifier();
                String ticketStatus = item.TicketStatus();
                String description = item.Description();
                String creationUserName = item.CreationUserName();

                messageBuilder.append("\n").append("Número do chamado: ").append(ticketIdentifier).append("\n")
                        .append("Status: ").append(ticketStatus).append("\n")
                        .append("Solicitante: ").append(creationUserName).append("\n")
                        .append("Descrição: ").append(description)
                        .append("\n_______________________________\n");
            }
        }
        return messageBuilder.toString();
    }
}