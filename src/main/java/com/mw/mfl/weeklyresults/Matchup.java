package com.mw.mfl.weeklyresults;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Matchup {

    @JsonProperty("franchise")
    private List<Franchise> franchises;

	public List<Franchise> getFranchises() {
		return franchises;
	}

	public void setFranchises(List<Franchise> franchises) {
		this.franchises = franchises;
	}

}
