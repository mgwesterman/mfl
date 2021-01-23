package com.mw.mfl.players;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	@JsonProperty("players")
    private Players players;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Players {
		@JsonProperty("player")
		private List<Player> playerList;
	}
}
