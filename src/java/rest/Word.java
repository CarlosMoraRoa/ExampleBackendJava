/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;


import java.util.regex.*;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 * REST Web Service
 *
 * @author cmora
 */
@Stateless
@Path("/word")
public class Word {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of word
     */
    public Word() {
    }

    /**
     * Retrieves representation of an instance of rest.word
     * @param jsonData
     * @return an instance of java.lang.String
     * @throws org.codehaus.jettison.json.JSONException
     */
    
    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response word(JsonObject jsonData) throws JSONException
    {
        try{

        String word = jsonData.getJsonString("data").toString().trim().replaceAll("\"", "");
         //Assign the regular expression to the pattern
        Pattern pattern = Pattern.compile("^[a-z]{4}$");
        Matcher m = pattern.matcher(word);
        JSONObject json = new JSONObject();

        
        if(m.matches())
        {    
            json.put("code", "00");
            json.put("description", "Ok");
            json.put("data", word.toUpperCase());
            
            
            return Response.status(200).entity(json.toString()).build();
        }
        
            json = new JSONObject();
            json.put("code", 400);
            json.put("description", "Bad Request");
            
            return Response.status(400).entity(json.toString()).build();      
       
    }catch(Exception e)
    {    
        JSONObject json = new JSONObject();
        json.put("code", 500);
        json.put("description", "Internal Error Server");
        return Response.status(500).entity(json.toString()).build();
    }       
    }
}
