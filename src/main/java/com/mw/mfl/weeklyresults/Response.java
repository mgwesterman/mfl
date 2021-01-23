package com.mw.mfl.weeklyresults;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @JsonProperty("weeklyResults")
    private WeeklyResults weeklyResults;

 }