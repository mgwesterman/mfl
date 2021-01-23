package com.mw.mfl.players;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    @JsonProperty("name")
    private String playerName;

    @JsonProperty("position")
    private String position;

    @JsonProperty("id")
    private String playerId;

    @JsonProperty("team")
    private String team;

    private String year;
}
