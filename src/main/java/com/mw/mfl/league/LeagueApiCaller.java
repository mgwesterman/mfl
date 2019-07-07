package com.mw.mfl.league;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.ObjectMapper;

class LeagueApiCaller 
{

    static final String YEAR = "2018";
    static final String TYPE = "?TYPE=league";
    static final String LEAGUE_ID = "&L=38387";
    static final String JSON = "&JSON=1";
    static final String SINCE = "SINCE";
    static final String EXPORT = "/export";
   
    static final String REQUEST_URL = "https://www63.myfantasyleague.com/";
    
    public static void main(String[] args) 
    {
        System.out.print(
        		"Year"  + "$" + 
                "Franchise Id" 		+ "$" +
        		"Franchise Name" 	+ "\n");
        
	    try 
	    {
	    	URL url = new URL(REQUEST_URL + YEAR + EXPORT + TYPE + LEAGUE_ID + JSON); 
	    //System.out.println(REQUEST_URL + YEAR + EXPORT + TYPE + LEAGUE_ID + JSON); 

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

	    	printResults(in);
        } 
	    catch (IOException ex) 
	    {
            ex.printStackTrace();
        }
    }

    static void printResults(InputStream in) 
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
            Response response = mapper.readValue(in, Response.class);

            League l  = response.getLeague();
            for (Franchise f : l.getFranchises().getFranchises()) 
            {
                System.out.print(
                		YEAR 	+ "$" +
                        f.getId() 		+ "$" +
                		f.getName() + "\n");
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
