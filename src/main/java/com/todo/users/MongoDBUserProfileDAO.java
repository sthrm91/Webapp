package com.todo.users;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBUserProfileDAO 
{
	 private DBCollection col;
	 
	 public MongoDBUserProfileDAO(MongoClient mongoClient, String dbName, String collection) 
	 {
		 this.col = mongoClient.getDB(dbName).getCollection(collection);
	 }
	 
	 public UserProfile createUserProfile(UserProfile userProfile)
	 {
		 DBObject doc = UserProfileConverter.toDBObject(userProfile);
	     this.col.insert(doc);
	     ObjectId id = (ObjectId) doc.get("_id");
	     userProfile.setId(id.toString());
		 return userProfile;
	 }
	 
	 public UserProfile getProfileByUserName(String userName)
	 {
		 BasicDBObject query = new BasicDBObject("userName", userName);        
	     DBCursor cursor = col.find(query);
	     
	     if(cursor.count() == 0)
	     {
	    	 return null;
	     }
	     
	     return UserProfileConverter.toUserProfile(cursor.next());	        
	 }
}
