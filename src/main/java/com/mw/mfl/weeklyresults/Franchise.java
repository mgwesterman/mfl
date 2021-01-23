package com.mw.mfl.weeklyresults;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Franchise {

    @JsonProperty("player")
    private List<Player> players;

    @JsonProperty("id")
    private String id;

	@JsonProperty("result")
	private String result;

	@JsonProperty("score")
	private String score;

	private String week;
	private String year;
	private String opponent;
	private String opponentScore;

}
