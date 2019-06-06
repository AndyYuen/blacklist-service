package com.redhat.demo.blacklist.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.myspace.datavalidation.Person;
import com.redhat.demo.blacklist.Util;


@Component
@Path("/")
public class BlacklistEndpoint {

	static String SEPARATOR = "####################################################################################";

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
    	System.out.println(SEPARATOR);
    	System.out.println("Check if on blacklist invoked with name: " + name);
    	String result = "no";
    	if (blacklist.containsKey(name)) {
    		result = "yes";
    	}
    	System.out.println(name + " in blacklist: " + result);

    	return String.format("{ \"result\" : \"%s\" }", result);
    }
    
    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String validateName( Person person) throws IOException, JSONException, AuthenticationException {
    	System.out.println(SEPARATOR);
    	System.out.println("Person with name: " + person.getName());
    	String commands = String.format(readResource("DMcommands.json"), person.getName());
    	System.out.println("parameter: " + commands);
    	System.out.println(SEPARATOR);
    	String result = Util.invokeDM(commands);
    	return result;
    }
    

	
	
	public String readResource(String resourceName) throws IOException, JSONException {
//		System.out.println("***********" + resourceName);
	    InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
	    String content = IOUtils.toString(is, "utf-8");
//		System.out.println("Content:" + content);
	    return content;
	}
}