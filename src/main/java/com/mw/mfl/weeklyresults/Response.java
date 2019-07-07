package com.mw.mfl.weeklyresults;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    public WeeklyResults getWeeklyResults() {
		return weeklyResults;
	}

	public void setWeeklyResults(WeeklyResults weeklyResults) {
		this.weeklyResults = weeklyResults;
	}

	@JsonProperty("version")
    private String version;

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@JsonProperty("weeklyResults")
    private WeeklyResults weeklyResults;

    public Response() { }

}
