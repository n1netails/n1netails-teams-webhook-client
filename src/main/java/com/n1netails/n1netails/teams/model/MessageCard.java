package com.n1netails.n1netails.teams.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Teams Webhook Message Card
 */
@Getter
@Setter
public class MessageCard {

    @SerializedName("@type")
    private String type = "MessageCard";
    private String title;
    private String summary;
    private List<Section> sections;

    /**
     * Message Card Constructor
     */
    public MessageCard() {}
}
