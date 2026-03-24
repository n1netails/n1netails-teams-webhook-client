package com.n1netails.n1netails.teams.service;

import com.n1netails.n1netails.teams.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class WebhookServiceTest {

    @Test
    public void testGetWebhookPayloadFromWebhookMessage() {
        WebhookMessage message = new WebhookMessage();
        message.setContent("Test Content");
        message.setImageUrl("http://example.com/image.png");
        message.setActions(Collections.singletonList(new Action("Action", "http://example.com")));

        WebhookPayload payload = WebhookService.getWebhookPayload(message);

        assertNotNull(payload);
        assertEquals(1, payload.getAttachments().size());
        WebhookPayload.Attachment attachment = payload.getAttachments().get(0);
        assertEquals(WebhookService.APPLICATION_VND_MICROSOFT_CARD_ADAPTIVE, attachment.getContentType());

        WebhookPayload.Content content = attachment.getContent();
        assertEquals(WebhookService.HTTP_ADAPTIVECARDS_IO_SCHEMAS_ADAPTIVE_CARD_JSON, content.get$schema());
        assertEquals(WebhookService.ADAPTIVE_CARD, content.getType());
        assertEquals(WebhookService.VERSION, content.getVersion());

        assertEquals(2, content.getBody().size());

        WebhookPayload.BodyItem textItem = content.getBody().get(0);
        assertEquals(WebhookService.TEXT_BLOCK, textItem.getType());
        assertEquals("Test Content", textItem.getText());

        WebhookPayload.BodyItem imageItem = content.getBody().get(1);
        assertEquals(WebhookService.IMAGE, imageItem.getType());
        assertEquals("http://example.com/image.png", imageItem.getUrl());

        assertNotNull(content.getActions());
        assertEquals(1, content.getActions().size());
        assertEquals("Action", content.getActions().get(0).getTitle());
    }

    @Test
    public void testGetWebhookPayloadFromMessageCard() {
        MessageCard card = new MessageCard();
        card.setTitle("Test Title");
        card.setSummary("Test Summary");

        Section section = new Section();
        section.setTitle("Section Title");
        section.setImageUrl("http://example.com/section-image.png");
//        section.setVideoUrl("http://example.com/section-video.mp4");
        card.setSections(Collections.singletonList(section));

        Action action = new Action("View More", "http://example.com");
        card.setActions(Collections.singletonList(action));

        WebhookPayload payload = WebhookService.getWebhookPayload(card);

        assertNotNull(payload);
        WebhookPayload.Content content = payload.getAttachments().get(0).getContent();

        assertEquals(4, content.getBody().size());
        assertEquals("Test Title", content.getBody().get(0).getText());
        assertEquals("Test Summary", content.getBody().get(1).getText());
        assertEquals("Section Title", content.getBody().get(2).getText());

        assertEquals(WebhookService.IMAGE, content.getBody().get(3).getType());
        assertEquals("http://example.com/section-image.png", content.getBody().get(3).getUrl());

        assertNotNull(content.getActions());
        assertEquals(1, content.getActions().size());
        assertEquals(WebhookService.ACTION_OPEN_URL, content.getActions().get(0).getType());
        assertEquals("View More", content.getActions().get(0).getTitle());
        assertEquals("http://example.com", content.getActions().get(0).getUrl());
    }
}
