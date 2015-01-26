package com.todo.app;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.todo.activities.ActivitiesRestResource;
import com.todo.users.UserRestResource;
 
/**
 * Registers the components to be used by the JAX-RS application  
 * 
 * @author Sethu
 *
 */
public class SearchApplication extends ResourceConfig {
 
    /**
    * Register JAX-RS application components.
    */    
    public SearchApplication()
    {
    	register(RequestContextFilter.class);
		register(UserRestResource.class);
		register(ActivitiesRestResource.class);
		register(JacksonFeature.class);				       
    }
}
