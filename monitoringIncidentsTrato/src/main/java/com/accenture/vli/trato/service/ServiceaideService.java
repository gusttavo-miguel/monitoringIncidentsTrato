package com.accenture.vli.trato.service;

import com.accenture.vli.trato.credentials.ServiceaideData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.io.IOException;

public class ServiceaideService {

    public String getIncidents() throws IOException, URISyntaxException {

        var serviceaideData = new ServiceaideData();

        HttpURLConnection conn = getHttpURLConnection(serviceaideData);

        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        conn.disconnect();

        return response.toString();
    }

    private static HttpURLConnection getHttpURLConnection(ServiceaideData serviceaideData) throws URISyntaxException, IOException {

        URL url = new URL(serviceaideData.getAPI_URL());

        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
                url.getQuery(), url.getRef());

        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("csm_app_url", serviceaideData.getCSM_APP_URL());
        conn.setRequestProperty("user_auth_token", serviceaideData.getUSER_AUTH_TOKEN());
        conn.setRequestProperty("slice_token", serviceaideData.getSLICE_TOKEN());
        conn.setRequestProperty("webservice_user_name", serviceaideData.getWEBSERVICE_USER_NAME());
        conn.setRequestProperty("webservice_user_password", serviceaideData.getWEBSERVICE_USER_PASSWORD());
        return conn;
    }
}