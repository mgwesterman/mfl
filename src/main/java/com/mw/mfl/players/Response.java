package com.mw.mfl.players;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @JsonProperty("version")
    private String version;

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setPlayers(Players players) {
		this.players = players;
	}

	@JsonProperty("players")
    private Players players;
 //   private ArrayList<Player> playerList;

    public Response() { }

    public Players getPlayers() {
    	return players;
    }
}
