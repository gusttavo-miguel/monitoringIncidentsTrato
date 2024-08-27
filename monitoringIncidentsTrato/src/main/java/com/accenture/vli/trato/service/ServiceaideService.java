package com.accenture.vli.trato.service;

import com.accenture.vli.trato.credentials.ServiceaideData;
import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.io.IOException;

public class ServiceaideService {

    public String getIncidents() throws IOException, URISyntaxException {

        var serviceaideData = new ServiceaideData();

        String projectId = "vli-integrarodo-dev";
        String versionId = "latest";
        String secretIdSliceToken = "slicetoken-serviceaide-telegram";
        String secretIdApiUserAuthToken = "apiuserauthtoken-serviceaide-telegram";
        String secretIdWebServicePwd = "apiuserauthtoken-serviceaide-telegram";
        String secretIdWebServiceUser = "apiuserauthtoken-serviceaide-telegram";

        String secretIdSliceTokenValue = accessSecretVersion(projectId, secretIdSliceToken,versionId);
        String secretIdApiUserAuthTokenValue = accessSecretVersion(projectId, secretIdApiUserAuthToken,versionId);
        String secretIdWebServicePwdValue = accessSecretVersion(projectId, secretIdWebServicePwd,versionId);
        String secretIdWebServiceUserValue = accessSecretVersion(projectId, secretIdWebServiceUser,versionId);

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

    public static String accessSecretVersion(String projectId, String secretId, String versionId) {
        // Inicializa o cliente do Secret Manager
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {

            // Constroi o nome do segredo
            SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, versionId);

            // Acessa o segredo
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);

            // Retorna o valor do segredo como string
            return response.getPayload().getData().toStringUtf8();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}