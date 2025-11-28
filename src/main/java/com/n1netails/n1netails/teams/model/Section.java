package com.n1netails.n1netails.teams.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Teams Webhook Message Section
 */
@Getter
@Setter
public class Section {

    private String title;
    private List<Fact> facts;

    /**
     * Section Constructor
     */
    public Section() {}
}
