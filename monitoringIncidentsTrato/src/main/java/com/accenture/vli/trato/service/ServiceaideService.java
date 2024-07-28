package com.accenture.vli.trato.service;

import com.accenture.vli.trato.credentials.ServiceaideData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServiceaideService {

    public String getIncidents() {

        var restTemplate = new RestTemplate();
        var serviceaideData = new ServiceaideData();
        var headers = new HttpHeaders();

        headers.set("csm_app_url", serviceaideData.getCSM_APP_URL());
        headers.set("user_auth_token", serviceaideData.getUSER_AUTH_TOKEN());
        headers.set("slice_token", serviceaideData.getSLICE_TOKEN());
        headers.set("webservice_user_name", serviceaideData.getWEBSERVICE_USER_NAME());
        headers.set("webservice_user_password", serviceaideData.getWEBSERVICE_USER_PASSWORD());

        var entity = new HttpEntity<String>(headers);

        var apiUrl = serviceaideData.getAPI_URL();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();

    }



//    public String getIncidents() throws IOException, InterruptedException {
//        ServiceaideData serviceaideData = new ServiceaideData();
//
//        // Validação da URL
//        URI apiUri;
//        try {
//            apiUri = new URI(serviceaideData.getAPI_URL());
//        } catch (URISyntaxException e) {
//            throw new IllegalArgumentException("URL da API inválida: " + serviceaideData.getAPI_URL(), e);
//        }
//
//        // Cria um cliente HTTP
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Constrói a requisição com os cabeçalhos
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(apiUri)
//                .header("csm_app_url", serviceaideData.getCSM_APP_URL())
//                .header("user_auth_token", serviceaideData.getUSER_AUTH_TOKEN())
//                .header("slice_token", serviceaideData.getSLICE_TOKEN())
//                .header("webservice_user_name", serviceaideData.getWEBSERVICE_USER_NAME())
//                .header("webservice_user_password", serviceaideData.getWEBSERVICE_USER_PASSWORD())
//                .GET()
//                .build();
//
//        // Envia a requisição e recebe a resposta
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Retorna o corpo da resposta
//        return response.body();
//    }
}