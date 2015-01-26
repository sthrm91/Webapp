package com.todo.users;


import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.exceptions.AccountCreationException;



@Component
@Path("/user")
public class UserRestResource {

    @GET
    @Produces("text/html")
    public  Response getIt() {
    	return Response.ok("<html><head><title>Welcome!</title></head></body></html>").build();
    }
    
    @POST
    @Produces("text/html")
    public Response doLogin(
    		@FormParam("username") String userName,
    		@FormParam("password") String password)
    {
    	UserProfile user = validationService.login(userName, password);
    	if(user != null)
    	{
    		NewCookie cookie = new  NewCookie("id", user.getId());
    		return Response.ok().cookie(cookie).build();
    	}
    	else
    	{
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    }
    
    @Path("register/{userName}")
    @Produces("text/html")
    @GET
    public Response isUserNameAvailable(@PathParam("userName") String userName)
    {
    	if(validationService.isUserNameAvailable(userName))
    	{
    		return Response.ok().entity("UserName available").build();
    	}
    	
    	return Response.status(404).entity("username taken").build();
    }
    
    @Path("register/")
    @Produces("text/html")
    @POST
    public Response register(
    		@FormParam("username") String userName,
    		@FormParam("password") String password,
    		@FormParam("phone") String phoneNumber)
    {
    	UserProfile userProfile;
		try 
		{
			userProfile = validationService.register(userName, password, phoneNumber);
			NewCookie cookie = new  NewCookie("id", userProfile.getId());
    		return Response.status(201).cookie(cookie).build();
		} 
		catch (AccountCreationException e) 
		{
			return Response.status(409).entity(e.getMessage()).build();		
		}   
    }
    
    @Autowired
    private ValidationAndRegistrationService validationService;
}
