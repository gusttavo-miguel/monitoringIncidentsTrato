package com.example.botTelegram.service;

import com.example.botTelegram.newPojo.Return;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ServiceaidService {

//    private final String apiUrl = "https://csm3.serviceaide.com/csmconnector/Ticket/300-4249";
    private final String apiUrl = "https://csm3.serviceaide.com/csmconnector/Ticket"; // Substitua pela URL da API

    public String getIncidents() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("csm_app_url", "https://csm3.serviceaide.com");
        headers.set("user_auth_token", "apiusertokenslicetoken40285");
        headers.set("slice_token", "VbxiZZ1IpsGe5T7L_l.t-Ho68629aCgt");
        headers.set("webservice_user_name", "wsIncidentes@serviceaide.trato");
        headers.set("webservice_user_password", "vD7@6!Hcx1");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        String json = response.getBody();

        Gson gson = new Gson();
        Return data = gson.fromJson(json, Return.class);

        // Processar a resposta conforme necessário
        System.out.println(data);

        return json;
    }
}