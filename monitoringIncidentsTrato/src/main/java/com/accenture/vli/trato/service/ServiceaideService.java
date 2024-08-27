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

        String projectId = "vli-integrarodo-dev";
        String versionId = "latest";

        String secretIdApiUserAuthToken = "apiuserauthtoken-serviceaide-telegram";
        String secretIdSliceToken = "slicetoken-serviceaide-telegram";
        String secretIdWebServiceUser = "webserviceuser-serviceaide-telegram";
        String secretIdWebServicePwd = "webservicepwd-serviceaide-telegram";

        String secretIdApiUserAuthTokenValue = accessSecretVersion(projectId, secretIdApiUserAuthToken,versionId);
        String secretIdSliceTokenValue = accessSecretVersion(projectId, secretIdSliceToken,versionId);
        String secretIdWebServiceUserValue = accessSecretVersion(projectId, secretIdWebServiceUser,versionId);
        String secretIdWebServicePwdValue = accessSecretVersion(projectId, secretIdWebServicePwd,versionId);

        URL url = new URL(serviceaideData.getAPI_URL());

        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
                url.getQuery(), url.getRef());

        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("csm_app_url", serviceaideData.getCSM_APP_URL());
        conn.setRequestProperty("user_auth_token", secretIdApiUserAuthTokenValue);
        conn.setRequestProperty("slice_token", secretIdSliceTokenValue);
        conn.setRequestProperty("webservice_user_name", secretIdWebServiceUserValue);
        conn.setRequestProperty("webservice_user_password", secretIdWebServicePwdValue);
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