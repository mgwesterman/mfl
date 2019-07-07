package com.mw.mfl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mw.mfl.players.Player;
import com.mw.mfl.players.Players;
import com.mw.mfl.players.Response;

class ApiCaller 
{

    static final String YEAR = "2018";
    static final String TYPE = "?TYPE=";
    static final String PLAYERS = "players";
    static final String LEAGUE_ID = "&L=38387";
    static final String JSON = "&JSON=1";
    static final String SINCE = "SINCE";
    static final String EXPORT = "/export";

   
    static final String REQUEST_URL = "https://api.myfantasyleague.com/";
    
    public static void main(String[] args) 
    {
    		
	    try 
	    {
	    	URL url = new URL(REQUEST_URL + YEAR + EXPORT + TYPE + PLAYERS + JSON); 

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

	    	printPlayers(in);
        } 
	    catch (IOException ex) 
	    {
            ex.printStackTrace();
        }
    }

    static void printPlayers(InputStream in) 
    {
        try 
        {
        	// These two lines of code take the input stream and return a POJO. 
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(in, Response.class);
            System.out.println(response.getVersion());
            Players players = response.getPlayers();
            for (Player player : players.getPlayerList()) 
            {
                System.out.print( 
                		player.getName() 	+ "$" + player.getId() + "\n");
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
