package com.todo.activities;

import java.util.List;

import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/activities/")
public class ActivitiesRestResource 
{
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public  Response getAllActivities(@CookieParam("id") String ownerId) 
	{
		if(ownerId != null)
		{
			List<Activity> activities = activityManagementService.getAllActivitiesFor(ownerId);
			if(activities != null)
			{
				return Response.status(Status.OK).entity(activities).build();
			}
			else
			{
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Encountered error in the server. Try again later.").build();
			}
		}
		else
		{
			return Response.status(Status.UNAUTHORIZED).entity("Wrong credentials!!").build();
		}
    }
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createActivity(@FormParam("title") String title,
			@FormParam("description") String description,
			@CookieParam("id") String ownerId)
	{
		Activity activity = new Activity();
		
		activity.setDescription(description);
		activity.setTitle(title);
		activity.setOwnerId(ownerId);
		activity.setDone(false);
		
		if(!activity.getOwnerId().equals(ownerId))
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if( activityManagementService.createActivity(activity))
		{
			return Response.status(Status.CREATED).entity(activity).build();
		}
		else
		{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@PUT
	@Path("{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateActivity(@FormParam("description") String description,
			@FormParam("done") boolean done,
			@CookieParam("id") String ownerId,
			@PathParam("activityId") String activityId)
	{
		
		Activity activity = activityManagementService.getActivityById(activityId);		
		
		/*
		 * retrieving original activity wasn't successful
		 */
		
		if(activity == null)
		{
			return Response.status(Status.BAD_REQUEST).build();
		}
		else if(!activity.getOwnerId().equals(ownerId))
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else 
		{
			
			activity.setDescription(description);
			activity.setDone(done);
			
			if(activityManagementService.updateActivity(activity))
			{
				return Response.status(Status.OK).entity(activity).build();
			}
			
			/*
			 * Update operation wasn't successful
			 */
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}	
		
	}	
	
	@DELETE
	@Path("{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteActivity(@PathParam("activityId") String activityId,
			@CookieParam("id") String ownerId)
	{
		Activity activity = activityManagementService.getActivityById(activityId);
		/*
		 * retrieving activity marked by the id wasn't successful
		 */
		if(activity == null)
		{
			return Response.status(Status.BAD_REQUEST).build();
		}
		else if(!activity.getOwnerId().equals(ownerId))
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else 
		{
			if(activityManagementService.deleteActivity(activity))
			{
				return Response.status(Status.OK).entity(activity).build();
			}
			
			/*
			 * delete operation wasn't successful
			 */
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			
		}
	}
	
	@Autowired
	private ActivityManagementService activityManagementService;
}
