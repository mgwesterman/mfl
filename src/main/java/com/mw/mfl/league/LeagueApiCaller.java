package com.mw.mfl.league;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mw.mfl.api.AbstractMflApiCaller;

public class LeagueApiCaller extends AbstractMflApiCaller
{

    public static void main(String[] args)
    {
        LeagueApiCaller caller = new LeagueApiCaller();
        caller.getData(args[0]);
    }

    public void getData(String aYear)
    {
        setYear(aYear);
        setType(LEAGUE);
        InputStream in = callApi();
        List<Franchise> franchises = compileResults(in);
        writeToFile(franchises, Franchise.class);
    }
    private List<Franchise> compileResults(InputStream in)
    {
        List<Franchise> franchises = new ArrayList<>();
        try {
            // These two lines of code take the input stream and return a POJO.
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(in, Response.class);

            response.getLeague().getFranchises().getFranchises().forEach(f -> {
                // the year isn't in the json payload but we want it in the output
                f.setYear(getYear());
            });
        }
        catch (IOException ex)  {  System.err.println("Could not parse JSON data: " + ex.getMessage()); }
        return franchises;
    }

    @Override
    public void writeToFile(List<?> output, Class<?> outputClass) {
        CsvSchema schema = getSchema(outputClass).sortedBy("year", "id","name");
            writeToFile(output,schema);
        }
}
