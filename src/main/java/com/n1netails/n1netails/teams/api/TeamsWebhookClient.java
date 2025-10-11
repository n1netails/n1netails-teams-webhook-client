package com.n1netails.n1netails.teams.api;

import com.n1netails.n1netails.teams.exception.TeamsWebhookException;
import com.n1netails.n1netails.teams.model.WebhookMessage;

/**
 * Teams Webhook Client
 * @author shahid foy
 */
public interface TeamsWebhookClient {

    /**
     * Send teams webhook message
     * @param webhookUrl teams webhook url
     * @param message teams message
     * @throws TeamsWebhookException teams webhook exception
     */
    void sendMessage(String webhookUrl, WebhookMessage message) throws TeamsWebhookException;
}
