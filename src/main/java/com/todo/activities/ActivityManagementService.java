package com.todo.activities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityManagementService 
{
	public List<Activity> getAllActivitiesFor(String ownerId)
	{
		try
		{
			List<Activity> activities = new ArrayList<>(activityDAO.readAllActivitiesByUser(ownerId));
			return activities;
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
	
	public Activity getActivityById(String id)
	{
		return activityDAO.getActivityById(id);
	}
	
	public boolean updateActivity(Activity activity)
	{
		try
		{
			activityDAO.updateActivity(activity);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	public boolean createActivity(Activity activity)
	{	
		try
		{
			activityDAO.createActivity(activity);
			return true;			
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	public boolean deleteActivity(Activity activity)
	{
		try
		{
			activityDAO.deleteActivity(activity);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}		
	}
	
	@Autowired
	private MongoDBActivityDAO activityDAO;
}
