package com.mw.mfl.weeklyresults;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    @JsonProperty("status")
    private String status;

    @JsonProperty("id")
    private String id;

	@JsonProperty("score")
    private String score;

	@JsonProperty("shouldStart")
    private String shouldStart;

	public String getId() {
		return id;
	}

	public String getScore() {
		return score;
	}

	public String getShouldStart() {
		return shouldStart;
	}

	public void setShouldStart(String shouldStart) {
		this.shouldStart = shouldStart;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setScore(String score) {
		this.score = score;
	}

    public void setStatus(String status) {
		this.status = status;
	}
}
