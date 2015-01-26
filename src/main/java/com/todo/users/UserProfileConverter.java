package com.todo.users;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class UserProfileConverter 
{
	public static DBObject toDBObject(UserProfile userProfile) 
	{
	    BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("userName", userProfile.getUsername())
                .append("phoneNumber", userProfile.getPhoneNumber())
	    		.append("password", userProfile.getPassword());
                
        if (userProfile.getId() != null)
        {
        	builder = builder.append("_id", new ObjectId(userProfile.getId()));
        }
        
        return builder.get();
    }
	
	public static UserProfile toUserProfile(DBObject doc) 
	{
		UserProfile userProfile = new UserProfile();
        userProfile.setUsername((String) doc.get("userName"));
        userProfile.setPassword((String) doc.get("password"));
        userProfile.setPhoneNumber((String) doc.get("phoneNumber"));
        ObjectId id = (ObjectId) doc.get("_id");
        userProfile.setId(id.toString());
        return userProfile;
    }

}
