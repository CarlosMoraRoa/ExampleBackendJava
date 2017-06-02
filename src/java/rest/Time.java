/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * REST Web Service
 *
 * @author cmora
 */
@Stateless
@Path("/time")
public class Time {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Time
     */
    public Time() {
    }

    /**
     * Get method for updating or creating an instance of word
     * @param value
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response time(@Context UriInfo ui) throws JSONException {
        //to handle the 500 error
        try{
            
       
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        //Remove characteres from string
        String hour = queryParams.get("value").get(0).trim().replaceAll("\"", "");
        JSONObject json = new JSONObject();
        //Assign the regular expression to the pattern
        Pattern pattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        Matcher m = pattern.matcher(hour);
        //
        if(m.matches()){
        
            int hours = Integer.parseInt(hour.substring(0, 1));
            int minutes = Integer.parseInt(hour.substring(4, 5));
            int seconds = Integer.parseInt(hour.substring(7, 8));
        
            //Set HH:MM:SS to DateTime with the queryParam 
            DateTime hourToConvert =DateTime.now().withHourOfDay(hours).withMinuteOfHour(minutes).withSecondOfMinute(seconds);
       
            DateTime hourUtc = hourToConvert.withZone(DateTimeZone.UTC);
            
            json.put("code", "00");
            json.put("description", "Ok");
            json.putOpt("data", hourUtc);
            
            
            return Response.status(200).entity(json.toString()).build();
            
        }

        json.put("code", 400);
        json.put("description", "Bad Request");
        return Response.status(400).entity(json.toString()).build();
    }catch(Exception e){
        JSONObject json = new JSONObject();
        json.put("code", 500);
        json.put("description", "Internal Error Server");
        return Response.status(500).entity(json.toString()).build();
    }
    }
}
