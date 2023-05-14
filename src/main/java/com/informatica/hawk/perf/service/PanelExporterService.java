package com.informatica.hawk.perf.service;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

import static com.informatica.hawk.perf.Constants.*;

public class PanelExporterService {
    private final OkHttpClient okHttpClient;

    public PanelExporterService() {
        this.okHttpClient = new OkHttpClient();
    }

    public void exportAndSave(String dashboardId, String panelId, String from, String to) throws IOException {


        String imageUrl = grafanaUrl + "/render/d-solo/" + dashboardId
                + "?orgId=1&panelId=" + panelId
                + "&from=" + from + "&to=" + to
                + "&width=" + width + "&height=" + height
                + "&tz=" + timeZone;

        String authString = userId + ":" + password;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        Request request = new Request.Builder()
                .url(imageUrl)
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        saveImage(response);
    }

    private void saveImage(Response response) throws IOException {
        if (response.isSuccessful()) {
            final String savePath = "downloaded-panels/Panel" + UUID.randomUUID() + ".PNG";
            if (response.body() != null) {
                try (InputStream inputStreamNew = response.body().byteStream();
                     OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(savePath))) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStreamNew.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        } else {
            System.out.println("Failed with code : " + response.code());
        }
    }
}
