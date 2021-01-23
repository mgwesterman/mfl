package com.mw.mfl.weeklyresults;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyResults {

    @JsonProperty("matchup")
    private List<Matchup> matchups;

    @JsonProperty("week")
    private String week;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Matchup {

        @JsonProperty("franchise")
        private List<Franchise> franchises;
    }
}
