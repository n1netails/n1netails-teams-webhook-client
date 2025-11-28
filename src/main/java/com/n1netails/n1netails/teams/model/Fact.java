package com.n1netails.n1netails.teams.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Teams Webhook Message Fact
 */
@Getter
@Setter
@AllArgsConstructor
public class Fact {

    private String name;
    private String value;
}
