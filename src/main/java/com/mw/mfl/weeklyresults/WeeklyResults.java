package com.mw.mfl.weeklyresults;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyResults {

    @JsonProperty("matchup")
    private List<Matchup> matchups;

    @JsonProperty("week")
    private String week;

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public List<Matchup> getMatchups() {
		return matchups;
	}

	public void setMatchups(List<Matchup> matchups) {
		this.matchups = matchups;
	}
}
