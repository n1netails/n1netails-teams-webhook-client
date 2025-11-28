package com.n1netails.n1netails.teams.internal;

import com.n1netails.n1netails.teams.api.TeamsWebhookClient;
import com.n1netails.n1netails.teams.exception.TeamsWebhookException;
import com.n1netails.n1netails.teams.model.MessageCard;
import com.n1netails.n1netails.teams.model.WebhookMessage;
import com.n1netails.n1netails.teams.service.WebhookService;

/**
 * Teams Webhook Client Implementation
 * @author shahid foy
 */
public class TeamsWebhookClientImpl implements TeamsWebhookClient {

    private final WebhookService webhookService;

    /**
     * Teams Webhook Client Implementation Constructor
     * @param webhookService teams webhook service
     */
    public TeamsWebhookClientImpl(WebhookService webhookService) { this.webhookService = webhookService; }

    /**
     * Send teams webhook message
     * @param webhookUrl teams webhook url
     * @param message teams message
     * @throws TeamsWebhookException teams webhook exception
     */
    @Override
    public void sendMessage(String webhookUrl, WebhookMessage message) throws TeamsWebhookException {
        webhookService.send(webhookUrl, message);
    }

    /**
     * Send teams webhook message
     * @param webhookUrl teams webhook url
     * @param messageCard teams message
     * @throws TeamsWebhookException teams webhook exception
     */
    @Override
    public void sendMessage(String webhookUrl, MessageCard messageCard) throws TeamsWebhookException {
        webhookService.send(webhookUrl, messageCard);
    }
}
