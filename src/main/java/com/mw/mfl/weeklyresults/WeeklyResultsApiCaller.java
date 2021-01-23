package com.mw.mfl.weeklyresults;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mw.mfl.ApiCaller;
import com.mw.mfl.api.AbstractMflApiCaller;

public class WeeklyResultsApiCaller extends AbstractMflApiCaller implements ApiCaller
{
    public static void main(String[] args)
    {
        WeeklyResultsApiCaller caller = new WeeklyResultsApiCaller();

        if (args.length==0) {
            System.exit(1);
            System.err.println("no args were passed for year or week");
        }
        if (args.length == 2) caller.getData(args[0],args[1]);
        else
            caller.getData(args[0]);
    }

    private void getData(String aYear, String aWeek)
    {
        int week = Integer.parseInt(aWeek);
        setYear(aYear);
        setType(WEEKLY_RESULTS);

        List<WeeklyResults> allWeeklyResults = callForAWeek(week);
        List<WeeklyPlayerResultOutput> wro = compilePlayerResults(allWeeklyResults);
        writeWeeklyPlayerResults(wro);

        List<WeeklyFranchiseResultOutput> wfo = compileFranchiseResults(allWeeklyResults);
        setType("franchiseResults");
        writeWeeklyFranchiseResults(wfo);
    }
    public void getData(String aYear)
    {
        setYear(aYear);
        setType(WEEKLY_RESULTS);

        List<WeeklyResults> allWeeklyResults = callForAllWeeks();
        List<WeeklyPlayerResultOutput> wro = compilePlayerResults(allWeeklyResults);
        writeWeeklyPlayerResults(wro);

        List<WeeklyFranchiseResultOutput> wfo = compileFranchiseResults(allWeeklyResults);
        setType("franchiseResults");
        writeWeeklyFranchiseResults(wfo);
    }

    /**
     * make the api call for each week
     */
    private List<WeeklyResults> callForAWeek(int aWeek)
    {
        List<WeeklyResults> wrList = new ArrayList<>();
        try {
            InputStream in = callApi(getYear(), getType(), aWeek);

            ObjectMapper mapper = new ObjectMapper();
            com.mw.mfl.weeklyresults.Response response = mapper.readValue(in, com.mw.mfl.weeklyresults.Response.class);

            wrList.add(response.getWeeklyResults());
        }
        catch (Exception e) { System.err.println("Error calling weekly results api." + e.getMessage()); e.printStackTrace(); }
        return wrList;
    }

    private List<WeeklyResults> callForAllWeeks()
    {
        List<WeeklyResults> allWeeklyResults = new ArrayList<>();
        for (int i=1; i<18; i++)
        {
            allWeeklyResults.addAll(callForAWeek(i));
            // try to avoid getting rate-limited
            try { Thread.sleep(1000); } catch (Exception e) {}
        }
        return allWeeklyResults;
    }

    private List<Franchise> getFranchiseResults(List<WeeklyResults> allWeeklyResults)
    {
        List<Franchise> franchises = new ArrayList<>();
        allWeeklyResults.forEach(wr -> wr.getMatchups().forEach(matchup -> {
            int i = 0;
            for (Franchise franchise : matchup.getFranchises()) {
                if (i == 0) {
                    franchise.setOpponent(matchup.getFranchises().get(1).getId());
                    franchise.setOpponentScore(matchup.getFranchises().get(1).getScore());
                } else if (i == 1) {
                    franchise.setOpponent(matchup.getFranchises().get(0).getId());
                    franchise.setOpponentScore(matchup.getFranchises().get(0).getScore());
                } else if (i > 1) System.err.println("unexpected condition in franchises in matchup");
                franchise.setWeek(wr.getWeek());
                franchises.add(franchise);
                i++;
            }
        }));
        return franchises;
    }

    private List<WeeklyPlayerResultOutput> compilePlayerResults(List<WeeklyResults> allWeeklyResults)
    {
        List<WeeklyPlayerResultOutput> wro = new ArrayList<>();
        getFranchiseResults(allWeeklyResults).forEach(f -> {
            f.getPlayers().forEach(p-> {
                WeeklyPlayerResultOutput output = new WeeklyPlayerResultOutput();
                output.setYear(getYear());
                output.setWeek(f.getWeek());
                output.setFranchiseId(f.getId());
                output.setPlayerId(p.getId());
                output.setPlayerStatus(p.getStatus());
                output.setShouldStart(p.getShouldStart());
                output.setPlayerScore(p.getScore());
                wro.add(output);
            });
        });
        return wro;
    }
    /**
     * convert from hierarchy to flat list for output
    */
    private List<WeeklyFranchiseResultOutput> compileFranchiseResults(List<WeeklyResults> allWeeklyResults)
    {
        List<WeeklyFranchiseResultOutput> wfo = new ArrayList<>();
        getFranchiseResults(allWeeklyResults).forEach(f-> {
            WeeklyFranchiseResultOutput output = new WeeklyFranchiseResultOutput();
            output.setYear(getYear());
            output.setWeek(f.getWeek());
            output.setFranchiseId(f.getId());
            output.setScore(f.getScore());
            output.setResult(f.getResult());
            output.setOpponent(f.getOpponent());
            output.setOpponentScore(f.getOpponentScore());

            double oppScore = Double.parseDouble(f.getOpponentScore());
            double score = Double.parseDouble(f.getScore());

            if ((score - oppScore > -5) && (score - oppScore < 0))
                output.setBadbeat("true");
            else
                output.setBadbeat("false");
            if ((score - oppScore < 5) && (score - oppScore > 0))
                output.setGoodWin("true");
            else
                output.setGoodWin("false");
            wfo.add(output);
        });
         return wfo;
    }

    private void writeWeeklyPlayerResults(List<WeeklyPlayerResultOutput> allWeeklyResults)
    {
        CsvSchema schema = getSchema(WeeklyPlayerResultOutput.class).sortedBy("year", "week","franchiseId","playerId","playerStatus","playerScore","shouldStart");
        writeToFile(allWeeklyResults,schema);
    }
    private void writeWeeklyFranchiseResults(List<WeeklyFranchiseResultOutput> allWeeklyResults)
    {
        writeToFile(allWeeklyResults, WeeklyFranchiseResultOutput.class);
    }
}
