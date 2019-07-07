package com.mw.mfl.league;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Franchises {

	@JsonProperty("franchise")
    private List<Franchise> franchises;

	public List<Franchise> getFranchises() {
		return franchises;
	}

	public void setFranchises(List<Franchise> franchises) {
		this.franchises = franchises;
	}

	
}
