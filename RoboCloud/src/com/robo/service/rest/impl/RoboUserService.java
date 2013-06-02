package com.robo.service.rest.impl;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class RoboUserService {
	
	public RoboUserService() {
	}
	
	Entity createUser() {
		 UserService userService = UserServiceFactory.getUserService();
		 User user = userService.getCurrentUser();
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Object points = new Long(1000);

		 Entity userEntity = new Entity("RobotUser", user.getEmail());
		 userEntity.setProperty("name", user.getNickname());
		 userEntity.setProperty("points", points);
		 datastore.put(userEntity);
		
		 return userEntity;
	}
	
	Entity getUserEntity() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Key key = KeyFactory.createKey("RobotUser", user.getNickname());

		try {
			Entity userEntity = datastore.get(key);
			return(userEntity);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return(null);
		} 

	}
	
	Entity getUserEntityByName(String user) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey("RobotUser", user);

		try {
			Entity userEntity = datastore.get(key);
			return(userEntity);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	String getUserName() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return(user.getEmail()); 
	}
	
	void updateUserPoints(Long points) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity userEntity = this.getUserEntity();
		Long bpoints = (Long) userEntity.getProperty("points");
		
		bpoints = bpoints + (points);
		if (bpoints < 0) {
			bpoints = (long)0;
		}
		userEntity.setProperty("points", bpoints);
		datastore.put(userEntity);
	}
	
	void updateUserEntityPoints(Entity userEntity, Long points) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Long bpoints = (Long) userEntity.getProperty("points");
		bpoints = bpoints + (points);
		if (bpoints < 0) {
			System.out.println("points less than 0");
			bpoints = (long)0;
		}
		System.out.println("updated points");
		userEntity.setProperty("points", bpoints);
		datastore.put(userEntity);
	}
	
	String getUsers() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("RobotUser");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		StringBuffer buff = new StringBuffer();
		
		for (Entity entity : list) {
			buff.append("Name ");
			buff.append(entity.getProperty("name"));
			buff.append(",");
			buff.append("Points ");
			buff.append(entity.getProperty("points"));
			buff.append("\n");
		}
		return buff.toString();		
	}

	public Entity getUser() {
		UserService userService = UserServiceFactory.getUserService();
		 User user = userService.getCurrentUser();
		 Entity userEntity = getUserEntityByName(user.getNickname());
		 if(userEntity == null){
			 userEntity = createUser();
		 }
		 return userEntity;
	}
	
	public String getUserPoints(){
		Entity user = getUser();
		return user.getProperty("points").toString();
	}
}
