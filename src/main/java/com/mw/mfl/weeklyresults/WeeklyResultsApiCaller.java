package com.mw.mfl.weeklyresults;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mw.mfl.weeklyresults.Franchise;
import com.mw.mfl.weeklyresults.Matchup;
import com.mw.mfl.weeklyresults.WeeklyResults;

class WeeklyResultsApiCaller 
{

    static final String YEAR = "2018";
    static final String TYPE = "?TYPE=";
    static final String RESULTS = "weeklyResults";
    static final String LEAGUE_ID = "&L=38387";
    static final String WEEK = "&W=";
    static final String JSON = "&JSON=1";
    static final String SINCE = "SINCE";
    static final String EXPORT = "/export";

   
    static final String REQUEST_URL = "https://www63.myfantasyleague.com/";
    
    public static void main(String[] args) 
    {
    		
        System.out.print(
        		"Year"  + "$" + 
        		"Week" 	+ "$" +
                "Franchise Id" 		+ "$" +
        		"Player Id" 	+ "$" + 
        		"Player Status" + "$" + 
        		"Should Start" + "$" + 
        		"Player Score" + "\n");
        
	    try 
	    {
	    	for (int i=1; i<2; i++)
	    	{
		    	
		    	URL url = new URL(REQUEST_URL + YEAR + EXPORT + TYPE + RESULTS + LEAGUE_ID + WEEK + i + JSON); 
	
		    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
	            // Check the HTTP status code for "200 OK"
	            int statusCode = connection.getResponseCode();
	            String encoding = connection.getContentEncoding();
	            if (statusCode != HttpURLConnection.HTTP_OK) 
	            {
	                System.err.printf("Server returned HTTP status: %s. %s%n%n",
	                        statusCode, connection.getResponseMessage());
	                System.exit(1);
	            }
		
	            InputStream in = connection.getInputStream();
	            // Ensure there is data
	            if (in == null) 
	            {
	                System.err.println("Response is empty.");
	                System.exit(1);
	            }
	
		    	printWeeklyResults(in);
	    	}
        } 
	    catch (IOException ex) 
	    {
            ex.printStackTrace();
        }
    }

    static void printWeeklyResults(InputStream in) 
    {
        try 
        {
        	//BufferedReader buf = new BufferedReader(new InputStreamReader(in));
        	//String line = null;
        	//while ((line = buf.readLine()) != null) {
            //    	System.out.println(line);
        //	}
        	
        	// These two lines of code take the input stream and return a POJO. 

        	ObjectMapper mapper = new ObjectMapper();
            com.mw.mfl.weeklyresults.Response response = mapper.readValue(in, com.mw.mfl.weeklyresults.Response.class);


            WeeklyResults wr = response.getWeeklyResults();
            for (Matchup m : wr.getMatchups()) 
            {
            	for (Franchise f : m.getFranchises())
            	{
            		for (com.mw.mfl.weeklyresults.Player p : f.getPlayers())
            		{
                        System.out.print(
                        		YEAR 	+ "$" +
                        		wr.getWeek() 	+ "$" +
                                f.getId() 		+ "$" +
                        		p.getId() 	+ "$" + 
                        		p.getStatus() + "$" + 
                        		p.getShouldStart() + "$" + 
                        		p.getScore() + "\n");
            		}
            	}
            }
        } 
        catch (IOException ex) 
        {
            System.err.println("Could not parse JSON data: " + ex.getMessage());
        }
    }

    static String getPropertyValue(String key) {
        String value = null;
        try 
        {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("mfl");
            value = resourceBundle.getString(key);
        } 
        catch (MissingResourceException ex) 
        {
            ex.printStackTrace();
            System.exit(1);
        }
        return value;
    }

}
