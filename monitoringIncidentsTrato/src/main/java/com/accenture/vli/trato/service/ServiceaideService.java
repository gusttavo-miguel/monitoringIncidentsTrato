package com.accenture.vli.trato.service;

import com.accenture.vli.trato.credentials.ServiceaideData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
}