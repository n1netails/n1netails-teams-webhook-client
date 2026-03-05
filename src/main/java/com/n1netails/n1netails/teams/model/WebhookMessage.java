package com.n1netails.n1netails.teams.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Teams Webhook Message
 * @author shahid foy
 */
@Getter
@Setter
public class WebhookMessage {

    private String content;
    private String imageUrl;
    private String videoUrl;

    /**
     * Webhook Message Constructor
     */
    public WebhookMessage() {}
}
