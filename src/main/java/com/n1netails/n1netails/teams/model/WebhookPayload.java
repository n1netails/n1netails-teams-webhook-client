package com.n1netails.n1netails.teams.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Teams Webhook Payload
 * @author shahid foy
 */
@Setter
@Getter
public class WebhookPayload {

    private List<Attachment> attachments;

    /**
     * Webhook Payload Constructor
     */
    public WebhookPayload() {}

    /**
     * Attachment
     */
    @Setter
    @Getter
    public static class Attachment {
        private String contentType;
        private Content content;

        /**
         * Attachment Constructor
         */
        public Attachment() {}
    }

    /**
     * Content
     */
    @Setter
    @Getter
    public static class Content {
        private String $schema;
        private String type;
        private String version;
        private List<BodyItem> body;

        /**
         * Content Constructor
         */
        public Content() {}

        /**
         * Set $schema
         * @param schema schema
         */
        public void set$schema(String schema) {
            this.$schema = schema;
        }

        /**
         * Get $schema
         * @return $schema
         */
        public String get$schema() {
            return this.$schema;
        }
    }

    /**
     * Body Item
     */
    @Setter
    @Getter
    public static class BodyItem {
        private String type;
        private String text;
        private String weight;
        private String size;

        /**
         * Body Item Constructor
         */
        public BodyItem() {}
    }
}
