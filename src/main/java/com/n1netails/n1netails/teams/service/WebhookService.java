package com.n1netails.n1netails.teams.service;

import com.google.gson.Gson;
import com.n1netails.n1netails.teams.exception.TeamsWebhookException;
import com.n1netails.n1netails.teams.model.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Teams Webhook Service
 * @author shahid foy
 */
public class WebhookService {

    public static final String APPLICATION_VND_MICROSOFT_CARD_ADAPTIVE = "application/vnd.microsoft.card.adaptive";
    public static final String HTTP_ADAPTIVECARDS_IO_SCHEMAS_ADAPTIVE_CARD_JSON = "http://adaptivecards.io/schemas/adaptive-card.json";
    public static final String ADAPTIVE_CARD = "AdaptiveCard";
    public static final String VERSION = "1.4";
    public static final String TEXT_BLOCK = "TextBlock";
    public static final String BOLDER = "Bolder";
    public static final String MEDIUM = "Medium";

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
        WebhookPayload payload = getWebhookPayload(messageCard);
        send(webhookUrl, payload);
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
        attachment.setContentType(APPLICATION_VND_MICROSOFT_CARD_ADAPTIVE);

        WebhookPayload.Content content = getContent(message);

        attachment.setContent(content);
        List<WebhookPayload.Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        payload.setAttachments(attachments);
        return payload;
    }

    private static WebhookPayload.Content getContent(WebhookMessage message) {
        WebhookPayload.Content content = new WebhookPayload.Content();
        content.set$schema(HTTP_ADAPTIVECARDS_IO_SCHEMAS_ADAPTIVE_CARD_JSON);
        content.setType(ADAPTIVE_CARD);
        content.setVersion(VERSION);
        WebhookPayload.BodyItem bodyItem = new WebhookPayload.BodyItem();
        bodyItem.setType(TEXT_BLOCK);
        bodyItem.setText(message.getContent());
        bodyItem.setWeight(BOLDER);
        bodyItem.setSize(MEDIUM);
        List<WebhookPayload.BodyItem> bodyItems = new ArrayList<>();
        bodyItems.add(bodyItem);
        content.setBody(bodyItems);
        return content;
    }

    public static WebhookPayload getWebhookPayload(MessageCard card) {
        WebhookPayload payload = new WebhookPayload();

        WebhookPayload.Attachment attachment = new WebhookPayload.Attachment();
        attachment.setContentType(APPLICATION_VND_MICROSOFT_CARD_ADAPTIVE);

        WebhookPayload.Content content = getContent(card);

        // Assign
        attachment.setContent(content);
        payload.setAttachments(Collections.singletonList(attachment));

        return payload;
    }

    private static WebhookPayload.Content getContent(MessageCard card) {
        WebhookPayload.Content content = new WebhookPayload.Content();
        content.set$schema(HTTP_ADAPTIVECARDS_IO_SCHEMAS_ADAPTIVE_CARD_JSON);
        content.setType(ADAPTIVE_CARD);
        content.setVersion(VERSION);

        List<WebhookPayload.BodyItem> body = new ArrayList<>();

        // Title block
        if (card.getTitle() != null) {
            WebhookPayload.BodyItem titleBlock = new WebhookPayload.BodyItem();
            titleBlock.setType(TEXT_BLOCK);
            titleBlock.setText(card.getTitle());
            titleBlock.setWeight(BOLDER);
            titleBlock.setSize(MEDIUM);
            body.add(titleBlock);
        }

        // Summary block (optional)
        if (card.getSummary() != null) {
            WebhookPayload.BodyItem summaryBlock = new WebhookPayload.BodyItem();
            summaryBlock.setType(TEXT_BLOCK);
            summaryBlock.setText(card.getSummary());
            summaryBlock.setWrap(true);
            body.add(summaryBlock);
        }

        // Sections → Facts
        if (card.getSections() != null) {
            for (Section section : card.getSections()) {

                if (section.getTitle() != null) {
                    WebhookPayload.BodyItem sectionTitle = new WebhookPayload.BodyItem();
                    sectionTitle.setType(TEXT_BLOCK);
                    sectionTitle.setText(section.getTitle());
                    sectionTitle.setWeight(BOLDER);
                    sectionTitle.setSize(MEDIUM);
                    sectionTitle.setSeparator(true);
                    body.add(sectionTitle);
                }

                if (section.getFacts() != null) {
                    for (Fact fact : section.getFacts()) {
                        WebhookPayload.BodyItem factBlock = new WebhookPayload.BodyItem();
                        factBlock.setType(TEXT_BLOCK);
                        factBlock.setText(fact.getName() + ": " + fact.getValue());
                        factBlock.setWrap(true);
                        body.add(factBlock);
                    }
                }
            }
        }

        content.setBody(body);
        return content;
    }
}
