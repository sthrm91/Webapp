package com.todo.activities;


import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ActivityConverter {
	
	public static DBObject toDBObject(Activity activity) {
		 
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("title", activity.getTitle())
                .append("description", activity.getDescription())
                .append("ownerId", activity.getOwnerId())  
                .append("isDone", activity.isDone());
        
        if (activity.getId() != null)
        {
        	builder = builder.append("_id", new ObjectId(activity.getId()));
        }
        
        return builder.get();
    }
 
    public static Activity toActivity(DBObject doc) {
    	Activity activity = new Activity();
        activity.setTitle((String) doc.get("title"));
        activity.setDescription((String) doc.get("description"));
        activity.setOwnerId((String) doc.get("ownerId"));
        activity.setDone((Boolean) doc.get("isDone"));
        ObjectId id = (ObjectId) doc.get("_id");
        activity.setId(id.toString());
        return activity;
 
    }

}
