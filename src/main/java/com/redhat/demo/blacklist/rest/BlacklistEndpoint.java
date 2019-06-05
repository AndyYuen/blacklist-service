package com.redhat.demo.blacklist.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.json.JSONException;
import org.springframework.stereotype.Component;


@Component
@Path("/")
public class BlacklistEndpoint {


	static Map<String, Integer> blacklist = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("john-wick", 1);
			put("james-bond", 1);
			put("jason-bourne", 1);
			put("indiana-jones", 1);
			put("han-solo", 1);
		}
	};
    
    @GET
    @Path("/blacklist/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPassengers(@PathParam("name") String name) throws IOException, JSONException {
    	System.out.println("Check if on blacklist invoked with name: " + name);
    	String result = "no";
    	if (blacklist.containsKey(name)) {
    		result = "yes";
    	}
    	System.out.println(name + " in blacklist: " + result);

    	return String.format("{ \"result\" : \"%s\" }", result);
    }
    

}