package com.todo.persistence;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.todo.activities.Activity;
import com.todo.activities.MongoDBActivityDAO;

public class MongoCheck {

	public static void main(String[] args)
	{
		try {
			MongoDBActivityDAO dao = new MongoDBActivityDAO(new MongoClient(), "todo", "Activities");
			Activity activity = new Activity();
			activity.setDescription("desc");
			activity.setTitle("title");
			activity.setDone(true);
			activity.setOwnerId("owner");
			dao.createActivity(activity);
			//System.out.println(activity.getId());
			for (Activity each : dao.readAllActivitiesByUser("owner"))
			{
				each.setDescription("Updated description");
				dao.updateActivity(each);				
				System.out.println(each.toString());
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
