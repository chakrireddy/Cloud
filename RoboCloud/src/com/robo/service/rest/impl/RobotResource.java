package com.robo.service.rest.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Path("/robot/")
public class RobotResource {
	Logger log = Logger.getLogger(RobotResource.class.getName());
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/list/")
	 public String listOfRobots() {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query gaeQuery = new Query("Robot");
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		 StringBuffer buff = new StringBuffer();
		 for (Entity entity : list) {
			buff.append(entity.getProperty("name"));
			buff.append(",");
		}
		 StringBuffer rob = new StringBuffer();
		 rob.append("Corners,Crazy,Fire,MyFirstRobot,RamFire,SittingDuck,SpinBot,Target,Tracker,TrackFire,Walls");
	        return rob.toString();
	    }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/listrobots/")
	 public String listRobots() {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query gaeQuery = new Query("Robot");
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		 StringBuffer buff = new StringBuffer();
		 for (int i = 0; i < list.size(); i++) {
			 if(i==0){
				 buff.append("[");
			 }
			 buff.append("\"");
			 Entity entity = list.get(i);
			 buff.append(entity.getProperty("name"));
			 //buff.append("\"");
			 if(i!=(list.size()-1)){
				 buff.append(",");
			 }
			 if(i==(list.size()-1)){
				 buff.append("]");
			 }
		}
	        return buff.toString();
	    }	 

	 
	 @GET
	 @Produces("text/plain")
	 @Path("/ctask/")
	 public String createTask() {
		// Build a task using the TaskOptions Builder pattern from ** above
	       Queue queue = QueueFactory.getDefaultQueue();
	       TaskOptions taskOptions = new TaskOptions(TaskOptions.Builder.withUrl("/rest/robot/rtask").method(TaskOptions.Method.GET));
	       queue.add(taskOptions);
		 return "done";
	    }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/rtask/")
	 public String runTask() {
		 URL url = null;
			try {
				log.info("Backendurl: "+BackendServiceFactory.getBackendService().getBackendAddress("example"));
				url = new URL("http://"+BackendServiceFactory.getBackendService().getBackendAddress("example")+"/startgame");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
			} catch (MalformedURLException e) {
				log.info(e.getMessage());
			} catch (IOException e) {
				log.info(e.getMessage());
			} 
		 return "";
	    }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/createrobot/{robotname}/{refRobot}/{shared}")	 
	 public String createRobots( @PathParam ("robotname") String roboname,
			 @PathParam ("refRobot") String refRobot,
			 @PathParam ("shared") Boolean shared) {
		 RobotServices robo = new RobotServices();
		 return(robo.createRobots(roboname, refRobot, shared));
	 }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/deleterobot/{robotname}")
	 public String deleteRobots(@PathParam ("robotname") String roboname) {
		 RobotServices robo = new RobotServices();
		 return(robo.deleteRobot(roboname));
	 }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/lists/")
	 public String listsOfRobots() {
		 RobotServices robo = new RobotServices();
		 ArrayList<Robots> robos = robo.getRobots();
		 JSONObject objj = new JSONObject();
		 JSONArray arr = new JSONArray();

		 for (Robots rob: robos) {
			 try {
				 JSONObject obj = new JSONObject();
				 obj.append("robot", rob.getRobots());
				 obj.append("score", rob.getScore());
				 obj.append("level", rob.getLevel());
				 obj.append("user", rob.getUser());
				 obj.append("games", rob.getGames());
				 obj.append("shared", rob.isShared());
				 arr.put(obj);
			 } catch (JSONException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }

		 }

		 try {
			 objj.put("RoboArray", arr);
		 } catch (JSONException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 return(objj.toString());
	 }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/robots/")
	 public String robots() {
		 JSONObject obj = new JSONObject();
		 List<String> a = new ArrayList<String>();
		 a.add("hi");
		 a.add("bye");
		 
		 try {
			obj.append("hi", "chakri");
			JSONArray arr = new JSONArray();
			JSONObject ch = new JSONObject();
			ch.put("obb","jjj");
			arr.put(ch);
			arr.put(ch);
			obj.put("array", arr);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		 
		 
		 //RobotServices robo = new RobotServices();
		 //return(robo.getRobos());
		 return obj.toString();
	 }
	 @GET
	 @Produces("text/plain")
	 @Path("/createbattle/{name}/{maxp}/{maxr}")
	 public String createBattle( @PathParam ("name") String battlename, 
			 @PathParam ("maxp") String maxplayers,
			 @PathParam ("maxr") Long rounds) {
		 SessionServices session = new  SessionServices();
		 return session.createBattleSession(battlename,maxplayers, rounds);
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/joinbattle/{battlename}/{robot}")
	 public String joinBattle( @PathParam ("battlename") String battlename, 
			 @PathParam ("robot") String robot) {
		 SessionServices session = new  SessionServices();
		 return session.joinBattle(battlename, robot);
	 }



	 @GET
	 @Produces("application/json")
	 @Path("/deletesession/{battlename}")
	 public String deletegamesession(@PathParam ("battlename") String battlename){
		 SessionServices session = new  SessionServices();
		 return session.deleteSession(battlename);
	 }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/deletesessions/")
	 public String deletegamesessions(){
		 SessionServices session = new  SessionServices();
		 return session.deleteAllSessions();
	 }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/deletesessionsdata/")
	 public String deletegamesessiondata(){
		 SessionServices session = new  SessionServices();
		 return session.deleteAllSessionsData();
	 }

	 @GET
	 @Produces("application/json")
	 @Path("/unjoin/{battlename}/{robot}")
	 public String unJoinBattle(@PathParam ("battlename") String battlename,
			 @PathParam ("robot") String robot){
		 SessionServices session = new  SessionServices();
		 return session.deleteSessionData(battlename, robot);
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/battlesessions/")
	 public String listOfBattlesSessions() {
		 SessionServices session = new  SessionServices();
		 return session.getSessionData();
	 }	 


	 @GET
	 @Produces("text/plain")
	 @Path("/battledata/{battlename}")
	 public String listOfBattlesData(@PathParam ("battlename") String battlename) {
		 SessionServices session = new  SessionServices();
		 return session.getSessionData();
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/startbattle/{battlename}")
	 public String startBattle(@PathParam ("battlename") String battlename) {
		 SessionServices session = new  SessionServices();
		 return session.startBattleAdaptor(battlename);
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/sessionscore/{battlename}")
	 public String sessionLevel(@PathParam ("battlename") String battlename) {
		 Long level = (long)0;
		 SessionServices session = new  SessionServices();
		 level = session.getSessionLevel(battlename, null);
		 return(level.toString());
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/sessionfilter/{level}/{maxplayers}")
	 public String sessionLevel(@PathParam ("level") Long level,
			 @PathParam ("maxplayers") Long maxp) {
		 SessionServices session = new  SessionServices();
		 return(session.setSessionFilter(level, maxp));
	 }
	 
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/createUser/")
	 public String createUser() {
		 RoboUserService rUS = new RoboUserService();
		 Entity userEntity = rUS.createUser();
		 return "user created";
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/showUser/")
	 public String showUser() {
		 RoboUserService rUS = new RoboUserService();
		 return(rUS.getUsers());
	 }	 
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/loadRobots/")
	 public String loadRobots() {
		 RobotServices rs = new RobotServices();
		 return rs.createBulkRobots();
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/loadFilterRobots/{level}/{score}")
	 public String loadFilterRobots(@PathParam ("level") Long level,
			 @PathParam ("score") Long score) {
		 	RobotServices rs = new RobotServices();
		return rs.getRobotWithFilter(level, score); 
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/unjoinlist/")
	 public String unjoinlist() {
		 SessionServices ss = new SessionServices();
		 return ss.getUserBattles();
	 }
	 
	 @GET
     @Produces("text/plain")
     @Path("/loadResults/{battlename}")
     public String loadResults(@PathParam ("battlename") String battlename ) {
                     SessionServices s = new SessionServices();
                     return s.loadResult(battlename); 
     }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/robotinfo/{robot}")
	 public String robotInfo(@PathParam ("robot") String robot) {
		 RobotServices robo = new RobotServices();
		 Robots rob = robo.getRobotInfo(robot);
		 JSONObject obj = new JSONObject();
		 try {
			 obj.append("robot", rob.getRobots());
			 obj.append("score", rob.getScore());
			 obj.append("level", rob.getLevel());
			 obj.append("user", rob.getUser());
			 obj.append("games", rob.getGames());
			 obj.append("shared", rob.isShared());
		 } catch (JSONException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 return(obj.toString());
	 }
	 
	 @GET
	 @Produces("application/json")
	 @Path("/getBattleData/{battlename}")
	 public String getBattleData(@PathParam ("battlename") String battlename ) {
		 BattleData battle = new BattleData();
		 ArrayList<BattleSessionData> bsds = new ArrayList<BattleSessionData>();  
		 SessionServices s = new SessionServices();
		 battle = s.getBattleData(battlename);
		 bsds = battle.getBdata();
		 
		 JSONObject bat = new JSONObject();
		 JSONArray bDataArray = new JSONArray();
		 
		 for (BattleSessionData bsd: bsds) {
			 try {
				 JSONObject bs = new JSONObject();
				 //bs.put("user", bsd.getUser());
				 //bs.append("robot", bsd.getRobot());
				 //bs.append("score", bsd.getScore());
				 JSONArray arr = new JSONArray();
				 arr.put(bsd.getRobot());
				 arr.put(bsd.getScore());
				 arr.put(bsd.getUser());
				 arr.put(battle.getLevel());
				 //bs.put("robot", bsd.getRobot());
				 //bs.put("score", bsd.getScore());
				 bDataArray.put(arr);
			 } catch (Exception e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }

		 }

		 try {
			 //bat.put("battlename",battlename );
			 //bat.put("level",battle.getLevel());
			 //bat.put("maxp",battle.getMaxplayers());
			 //bat.put("player",battle.getPlayers());
			 //bat.put("rounds",battle.getRounds());
			 //bat.put("user",battle.getUser());
			 bat.put("aaData", bDataArray);
		 } catch (JSONException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 return bat.toString();
	 }
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/getUserScore/")
	 public String getUserScore(){
		 RoboUserService rbu = new RoboUserService();
		 return rbu.getUserPoints();
	 }
			 
}
