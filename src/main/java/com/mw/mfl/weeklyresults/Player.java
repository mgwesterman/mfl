package com.mw.mfl.weeklyresults;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
}
