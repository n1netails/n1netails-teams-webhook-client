package com.n1netails.n1netails.teams.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Action for MessageCard
 */
@Getter
@Setter
public class Action {
    private String name;
    private String target;

    /**
     * Action Constructor
     */
    public Action() {}

    /**
     * Action Constructor with parameters
     * @param name Name of the action
     * @param target Target URL of the action
     */
    public Action(String name, String target) {
        this.name = name;
        this.target = target;
    }
}
