package com.todo.activities;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class MongoDBActivityDAO 
{
    private DBCollection col;
 
    public MongoDBActivityDAO(MongoClient mongoClient, String dbName, String collection) 
    {
        this.col = mongoClient.getDB(dbName).getCollection(collection);
    }
    
    public Activity createActivity(Activity activity) 
    {
        DBObject doc = ActivityConverter.toDBObject(activity);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        activity.setId(id.toString());
        return activity;
    }
    
    public void updateActivity(Activity activity) 
    {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(activity.getId())).get();
        this.col.update(query, ActivityConverter.toDBObject(activity));
    }
    
    public List<Activity> readAllActivitiesByUser(String ownerId) 
    {
        List<Activity> data = new ArrayList<Activity>();
        BasicDBObject query = new BasicDBObject("ownerId", ownerId);        
        DBCursor cursor = col.find(query);
        
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Activity activity = ActivityConverter.toActivity(doc);
            data.add(activity);
        }        
        return data;
    }
    
    public void deleteActivity(Activity activity) 
    {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(activity.getId())).get();
        this.col.remove(query);
    }
    
    public Activity getActivityById(String id)
    {
    	DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(id)).get();
    	DBCursor cursor = col.find(query);
    	if(cursor.hasNext())
    	{
    		return ActivityConverter.toActivity(cursor.next());
    	}
    	
    	return null;
    }

}
