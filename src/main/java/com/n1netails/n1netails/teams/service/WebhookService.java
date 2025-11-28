package com.n1netails.n1netails.teams.service;

import com.google.gson.Gson;
import com.n1netails.n1netails.teams.exception.TeamsWebhookException;
import com.n1netails.n1netails.teams.model.MessageCard;
import com.n1netails.n1netails.teams.model.WebhookMessage;
import com.n1netails.n1netails.teams.model.WebhookPayload;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
        WebhookPayload payload = getWebhookPayload(message);
        send(webhookUrl, payload);
    }

    /**
     * Send teams webhook message
     * @param webhookUrl teams webook url
     * @param messageCard teams message
     * @throws TeamsWebhookException teams webhook exception
     */
    public void send(String webhookUrl, MessageCard messageCard) throws TeamsWebhookException {
        send(webhookUrl, (Object) messageCard);
    }

    private void send(String webhookUrl, Object message) throws TeamsWebhookException {
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
            if (responseCode < 200 || responseCode >= 300) {
                throw new TeamsWebhookException("Failed to send webhook message. Response code: " + responseCode);
            }

        } catch (Exception e) {
            throw new TeamsWebhookException("Error sending webhook message", e);
        }
    }

    private static WebhookPayload getWebhookPayload(WebhookMessage message) {
        WebhookPayload payload = new WebhookPayload();
        WebhookPayload.Attachment attachment = new WebhookPayload.Attachment();
        attachment.setContentType("application/vnd.microsoft.card.adaptive");

        WebhookPayload.Content content = getContent(message);

        attachment.setContent(content);
        List<WebhookPayload.Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        payload.setAttachments(attachments);
        return payload;
    }

    private static WebhookPayload.Content getContent(WebhookMessage message) {
        WebhookPayload.Content content = new WebhookPayload.Content();
        content.set$schema("http://adaptivecards.io/schemas/adaptive-card.json");
        content.setType("AdaptiveCard");
        content.setVersion("1.4");
        WebhookPayload.BodyItem bodyItem = new WebhookPayload.BodyItem();
        bodyItem.setType("TextBlock");
        bodyItem.setText(message.getContent());
        bodyItem.setWeight("Bolder");
        bodyItem.setSize("Medium");
        List<WebhookPayload.BodyItem> bodyItems = new ArrayList<>();
        bodyItems.add(bodyItem);
        content.setBody(bodyItems);
        return content;
    }
}
