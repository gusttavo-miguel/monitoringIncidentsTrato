package com.accenture.vli.trato.service;

import com.accenture.vli.trato.credentials.ServiceaideData;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServiceaideService {

    public String getIncidents() {

//        var restTemplate = new RestTemplate();
//        var serviceaideData = new ServiceaideData();
//        var headers = new HttpHeaders();
//
//        headers.set("csm_app_url", serviceaideData.getCSM_APP_URL());
//        headers.set("user_auth_token", serviceaideData.getUSER_AUTH_TOKEN());
//        headers.set("slice_token", serviceaideData.getSLICE_TOKEN());
//        headers.set("webservice_user_name", serviceaideData.getWEBSERVICE_USER_NAME());
//        headers.set("webservice_user_password", serviceaideData.getWEBSERVICE_USER_PASSWORD());
//
//        var entity = new HttpEntity<String>(headers);
//
//        var apiUrl = serviceaideData.getAPI_URL();
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
//
//        return response.getBody();

        // Cria um HttpClient
        HttpClient client = HttpClient.newHttpClient();
        var serviceaideData = new ServiceaideData();

        var filter = URLEncoder.encode(serviceaideData.getFILTER(), StandardCharsets.UTF_8);

        // Cria a requisição com o header
        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(serviceaideData.getAPI_URL()))
                .uri(URI.create(serviceaideData.getAPI_URL_PATH().concat(filter)))
                .header("Content-Type", "application/json")
                .header("csm_app_url", serviceaideData.getCSM_APP_URL())
                .header("user_auth_token", serviceaideData.getUSER_AUTH_TOKEN())
                .header("slice_token", serviceaideData.getSLICE_TOKEN())
                .header("webservice_user_name", serviceaideData.getWEBSERVICE_USER_NAME())
                .header("webservice_user_password", serviceaideData.getWEBSERVICE_USER_PASSWORD())
                .GET()
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assert response != null;
        return response.body();
    }

}