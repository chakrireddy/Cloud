package com.robo.service.rest.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class RobotServices {
	
	String  createRobots(String roboname, String refRobot, Boolean permission) {
		RoboUserService rUserService = new RoboUserService();
		rUserService.getUser();
		String user = rUserService.getUserName();
		
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Object level = new Long(Long.valueOf(1000));
		 Object score = new Long(Long.valueOf(0));
		 Object games = new Long(Long.valueOf(0));
		 Object rank = new Long(Long.valueOf(0));
		 Object shared = new Boolean(Boolean.valueOf(permission));
		 
		 /*
		 Entity ruser = rUserService.getUserEntity();
		 if (ruser == null) {
			 return ("user not found");
		 }
		 */
		 
		 Entity roboEntity = new Entity("Robot", roboname);
	        roboEntity.setProperty("name", roboname);
	        roboEntity.setProperty("refRobot", refRobot);
	        roboEntity.setProperty("user", user);
	        roboEntity.setProperty("level", level);
	        roboEntity.setProperty("score", score);
	        roboEntity.setProperty("rank", rank);
	        roboEntity.setProperty("games", games);
	        roboEntity.setProperty("shared", shared); 
		 datastore.put(roboEntity);
		//rUserService.updateUserPoints((long)100);
		 
		 System.out.println("robot "+roboname+" created by "+user);
	        return "robot "+roboname+" created by "+user;
	}
	
	void updateStats(List<RoboScores> scores) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		RoboUserService rUserService = new RoboUserService();
		
		this.rankRobots(scores);
		for (RoboScores score : scores) {
			Entity robot = this.getRobot(score.getRobot());
			if (robot == null) {
				System.out.println("Robot" + score.getRobot() + "not found");
				continue;
			}
			if(!score.isRecorded()) {
				Entity ruser = rUserService.getUserEntityByName((String) robot.getProperty("user"));
				if (ruser == null) {
					System.out.println("No User for robot" + (String) robot.getProperty("user"));
					continue;
				}
				Long points = score.getLevel() * 10;
				rUserService.updateUserEntityPoints(ruser, points);
				score.setRecorded(true);
				Long dscore = (Long)robot.getProperty("score");
				Long dgames = (Long)robot.getProperty("games");
				Long drank = (Long)robot.getProperty("rank");
				Long dlevel = (Long)robot.getProperty("level");
				dgames = dgames + 1;
				
				dscore = dscore + score.getScore();
				drank = drank + score.getRank();
				dlevel = dlevel + score.getLevel();
				
		        robot.setProperty("score", dscore);
		        robot.setProperty("games", dgames);
		        robot.setProperty("rank", drank);
		        robot.setProperty("level", dlevel);
		        datastore.put(robot);
		        System.out.println("Updated robot scores" + score.getRobot());
			}
			
		}
	}
	
	List<RoboScores> rankRobots(List<RoboScores> scores)
	{
			Collections.sort(scores);
			for (RoboScores score : scores) {
				score.setRank(scores.indexOf(score));
			}
			return(scores);
	}
	
	Long calculateLevelInc (Long baseline, Long roboScore, Long rank) {
		
		Long diff = Math.abs((baseline - roboScore));
		Long factor;
		
		if (diff == 0) {
			factor = (long)1;
		} else if (diff >= 100 && diff < 200) {
			factor = (long)2;
		} else {
			factor = (long)3;
		}
		return (factor * 10 * rank);
	}
	
	List <RoboScores> levelRobots(List<RoboScores> scores, String battlename) {
		SessionServices session = new SessionServices();
		Long rank = (long)scores.size()/2;
		
		for (RoboScores score: scores) {
			Entity robot = this.getRobot(score.getRobot());
			Entity battle = session.getBattleEntry(battlename);
			Long avgLevel = (Long)battle.getProperty("level");
			Long roboLevel = (Long)robot.getProperty("level");
			System.out.println("Rank" + score.getRank());
			score.setLevel(this.calculateLevelInc(avgLevel, roboLevel, rank));
			rank--;
		}
		return(scores);
	}
	
	ArrayList<Robots> getRobots() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Robot");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		ArrayList<Robots> robos = new ArrayList<Robots>();
		
		for (Entity entity : list) {
			Robots robo = new Robots();
			robo.setRobots((String)entity.getProperty("name"));
			robo.setScore((Long)entity.getProperty("score"));
			robo.setLevel((Long)entity.getProperty("level"));
			robo.setGames((Long)entity.getProperty("games"));
			robo.setUser((String)entity.getProperty("user"));
			robo.setShared((Boolean)entity.getProperty("shared"));
			robos.add(robo);
			/*
			buff.append("Name ");
			buff.append(entity.getProperty("name"));
			buff.append(",");
			buff.append("Score ");
			buff.append(entity.getProperty("score"));
			buff.append(",");
			buff.append("Level ");
			buff.append(entity.getProperty("level"));
			buff.append(",");
			buff.append("Games ");
			buff.append(entity.getProperty("games"));
			buff.append(",");
			buff.append("Rank ");
			buff.append(entity.getProperty("rank"));
			buff.append(",");
			buff.append("shared");
			buff.append((Boolean)entity.getProperty("shared"));
			buff.append("\n");
			*/
		}
		return(robos);
	}
	
	List<Robots> getRobos() {
		List<Robots> robots = new ArrayList<Robots>();
		Robots robot = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Robot");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : list) {
			robot = new Robots();
			robot.setRobots((String)entity.getProperty("name"));
			robot.setScore((Long)entity.getProperty("score"));
			robot.setLevel((Long)entity.getProperty("level"));
			robot.setGames((Long)entity.getProperty("games"));
			robots.add(robot);
		}
		return(robots);
	}
	
	List<String> getBattleRobots(String robots){
		ArrayList<String> battleRobots = new ArrayList<String>();
		Scanner tokenize = new Scanner(robots);
		while(tokenize.hasNext()) {
			battleRobots.add(tokenize.next());
		}
		return(battleRobots);
	}
	
	Entity getRobot(String robot) {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Key key = KeyFactory.createKey("Robot", robot);
		 Entity robotEntry = null;
		 try {
			robotEntry = datastore.get(key);
			return(robotEntry);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return(null);
		}
	}
	
	String deleteRobot(String robot) {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 RoboUserService rUserService = new RoboUserService();
		 
		 Entity robo = this.getRobot(robot);
		 String user = rUserService.getUserName();
		 
		 if (user == null) {
			 return("User not logged in");
		 }
		 
		 if (user.equals((String)robo.getProperty("user")) == false) {
			 return ("Invalid User");
		 }
		 datastore.delete(robo.getKey());
		 StringBuffer buff = new StringBuffer();
		 buff.append("Deleting" );
		 buff.append(robot);
		 buff.append(",");
		 return buff.toString();
	}
	
	String createBulkRobots() {
		String[] robots = {"sample.Corners", "sample.Crazy", "sample.Fire", "sample.MyFirstRobot", "sample.RamFire",
				"sample.SittingDuck", "sample.SpinBot", "sample.Target", "sample.Tracker", "sample.TrackFire", "sample.Walls"
		};
		for (String robot: robots) {
			this.createRobots(robot, "baseline", true);
		}
		return("Robots Added");
	}
	
	String getRobotWithFilter(Long level, Long score) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        StringBuffer buff = new StringBuffer();
        Filter levelFilter = new FilterPredicate("level",
                             FilterOperator.GREATER_THAN_OR_EQUAL,
                             level);
        
        Filter scoreFilter = new FilterPredicate("score",
                 FilterOperator.GREATER_THAN_OR_EQUAL,
                 score);
        
        Query gaeQuery = new Query("Robot").setFilter(levelFilter).setFilter(scoreFilter);
        PreparedQuery pq = datastore.prepare(gaeQuery);
        boolean first = true;
        for (Entity entity : pq.asIterable()) {
        	if(!first){
        		buff.append(",");	
        	}
            buff.append((String)entity.getProperty("name"));            
            first= false;
        }
        return(buff.toString());
   }
	
	String startBattleRobots(String battlename) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query gaeQuery = new Query("BattleData");
        PreparedQuery pq = datastore.prepare(gaeQuery);
        List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
        
        StringBuffer buff = new StringBuffer();
        boolean var = true;
        for (Entity entity : list) {
            Long cut;
            RoboUserService rUserService = new RoboUserService();
            Entity user = rUserService.getUserEntityByName((String)entity.getProperty("bUser"));
            Long points = (Long)user.getProperty("points");
            RobotServices rService = new RobotServices();
            Entity robo = rService.getRobot((String)entity.getProperty("bRobot"));
            Long roboLevel = (Long)robo.getProperty("level");
            String battle = (String)entity.getProperty("bBattle");
            if (battle.equals(battlename) == false) {
                    continue;
            }
            if (roboLevel < 1000) {
                cut = (long)5;
            } else if (roboLevel > 1000 && roboLevel < 1100){
                cut = (long)10;
            } else {
                cut = (long)30;
            }
            points = points - cut;
            user.setProperty("points", points);
            datastore.put(user);
            if (var == false) {
                buff.append(",");
            }
            var = false;
            buff.append((String)entity.getProperty("bRobot"));
        }
        return(buff.toString());
    }
	
	Robots getRobotInfo(String robot) {
		Entity entity = getRobot(robot);
		Robots robo = new Robots();
		robo.setRobots((String)entity.getProperty("name"));
		robo.setScore((Long)entity.getProperty("score"));
		robo.setLevel((Long)entity.getProperty("level"));
		robo.setGames((Long)entity.getProperty("games"));
		robo.setUser((String)entity.getProperty("user"));
		robo.setShared((Boolean)entity.getProperty("shared"));
		return(robo);
	}
}
