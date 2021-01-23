package com.mw.mfl.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

	@JsonProperty("league")
    private League league;

	@Data
    @JsonIgnoreProperties(ignoreUnknown = true)
	public static class League {
        @JsonProperty("franchises")
        private Franchises franchises;

        // creating a class hierarchy to represent the json structure
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Franchises {
            @JsonProperty("franchise")
            private List<Franchise> franchises;
        }
    }
}
