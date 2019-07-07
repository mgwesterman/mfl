package com.mw.mfl.league;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {

	@JsonProperty("franchises")
    private Franchises franchises;

	public Franchises getFranchises() {
		return franchises;
	}

	public void setFranchises(Franchises franchises) {
		this.franchises = franchises;
	}

	
}
