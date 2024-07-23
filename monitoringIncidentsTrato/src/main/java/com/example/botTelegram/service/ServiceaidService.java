package com.example.botTelegram.service;

import com.example.botTelegram.pojo.Return;
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


        HttpHeaders headers = new HttpHeaders();
        headers.set("csm_app_url", "https://csm3.serviceaide.com");
        headers.set("user_auth_token", "apiusertokenslicetoken40285");
        headers.set("slice_token", "VbxiZZ1IpsGe5T7L_l.t-Ho68629aCgt");
        headers.set("webservice_user_name", "wsIncidentes@serviceaide.trato");
        headers.set("webservice_user_password", "vD7@6!Hcx1");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = "https://csm3.serviceaide.com/csmconnector/Ticket?filter=((NonTranslatedTicketStatus ne 'Archive' and NonTranslatedTicketStatus ne 'Request - Delete' and NonTranslatedTicketStatus ne 'Closed' or (NonTranslatedTicketStatus eq 'Closed' and LastModTimestamp gt 1716433200)) and (OrgStatus eq 0) and (((AssignedGroupID eq '37' or AssignedGroupID eq '38') and (NonTranslatedTicketStatus eq 'New' or NonTranslatedTicketStatus eq 'Active' or NonTranslatedTicketStatus eq 'Queued') and (TypeName eq 'incident') and (TicketAgingRange eq '0-2 days'))))";
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        String json = response.getBody();

        Gson gson = new Gson();
        Return returnData = gson.fromJson(json, Return.class);

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\uD83E\uDD16 Incident Monitoring Bot\n");
        if (returnData != null) {
            messageBuilder.append("⚠\uFE0F Novo chamado(s) ativo(s) identificado(s)! ⚠\uFE0F\n");
            for (var item : returnData.data().items()) {
                String ticketIdentifier = item.TicketIdentifier();
                String ticketStatus = item.TicketStatus();
                String description = item.Description();

                messageBuilder.append("\n\n").append("Número do chamado: ").append(ticketIdentifier).append("\n").append("Status: ").append(ticketStatus).append("\n").append("Descrição: ").append(description).append("\n----------------------------------------------------------------");
            }
        } else {
            messageBuilder.append("Não foram identificados chamados recentes ativos!!");
        }
        return messageBuilder.toString();
    }
}