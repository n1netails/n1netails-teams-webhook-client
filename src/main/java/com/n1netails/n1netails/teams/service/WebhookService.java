package com.n1netails.n1netails.teams.service;

import com.google.gson.Gson;
import com.n1netails.n1netails.teams.exception.TeamsWebhookException;
import com.n1netails.n1netails.teams.model.WebhookMessage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Teams Webhook Service
 * @author shahid foy
 */
public class WebhookService {

    private final Gson gson = new Gson();

    /**
     * Webhook Service Constructor
     */
    public WebhookService() {}

    /**
     * Send teams webhook message
     * @param webhookUrl teams webook url
     * @param message teams message
     * @throws TeamsWebhookException teams webhook exception
     */
    public void send(String webhookUrl, WebhookMessage message) throws TeamsWebhookException {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = gson.toJson(message);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new TeamsWebhookException("Failed to send webhook message. Response code: " + responseCode);
            }

        } catch (Exception e) {
            throw new TeamsWebhookException("Error sending webhook message", e);
        }
    }
}
