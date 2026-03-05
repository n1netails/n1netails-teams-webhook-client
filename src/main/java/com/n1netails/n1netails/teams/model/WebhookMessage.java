package com.n1netails.n1netails.teams.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<Action> actions;

    /**
     * Webhook Message Constructor
     */
    public WebhookMessage() {}
}
