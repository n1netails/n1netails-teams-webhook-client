package com.n1netails.n1netails.teams.service;

import com.n1netails.n1netails.teams.exception.TeamsWebhookException;
import com.n1netails.n1netails.teams.model.WebhookMessage;

/**
 * Teams Webhook Service
 * @author shahid foy
 */
public class WebhookService {

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
        // todo implement sending message to teams channel
    }
}
