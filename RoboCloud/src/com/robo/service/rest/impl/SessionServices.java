package com.robo.service.rest.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SessionServices {
	
	String createBattleSession (String battlename, String maxplayers, Long rounds) {
		RoboUserService rUserService = new RoboUserService();
		String userName = (String) rUserService.getUser().getProperty("name");
		String user = rUserService.getUserName();
		Object players = new Long(0);
		Object level = new Long(0);
		Object maxp = new Long(Integer.valueOf(maxplayers));
		Date date = new Date();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity roboEntity = new Entity("Battle", battlename);
		roboEntity.setProperty("name", battlename);
		roboEntity.setProperty("user", userName);
		roboEntity.setProperty("state", "Active");
		roboEntity.setProperty("level", level);
		roboEntity.setProperty("players", players);
		roboEntity.setProperty("maxplayers", maxp);
		roboEntity.setProperty("rounds", rounds);
		roboEntity.setProperty("starttime", date);
		datastore.put(roboEntity);

		return "robot "+battlename+" created by "+user;
	}
	
	
	String joinBattle (String battlename, String robot) {
		RoboUserService rUserService = new RoboUserService();
		Entity userEntity = rUserService.getUser();
		String user = rUserService.getUserName();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		RobotServices roboServ = new RobotServices();

		Long players, maxplayers, level;
		List<RoboScores> scores;
		StringBuffer buff = new StringBuffer();


		Entity battleEntity = this.getBattleEntry(battlename);
		if (battleEntity == null) {
			return ("Battle " + battlename + " not found");
		}

		Entity robo = roboServ.getRobot(robot);
		if (robo == null) {
			return ("Robot" + robot + "not found");
		}

		players = (Long)battleEntity.getProperty("players");
		maxplayers = (Long)battleEntity.getProperty("maxplayers");

		if (players == null || maxplayers == null) {
			return "max player or player null";
		}
		
		/*
		 * Check permissions
		 */
		Boolean shared = (Boolean)robo.getProperty("shared");
		if (user == null) {
			return "User not found";
		}
		
		if (shared == false) {
			String usr = (String)robo.getProperty("user");
			if (usr.equals(user) == false) {
				return("No permission to use " + robot + "to user" + user);
			}
		}
		Long player = new Long(players + 1);
		if (player.longValue() > maxplayers.longValue()) {
			return("Cannot join more sessions");
			}
		/*
		 * Construct new entry for battle session.
		 */

		Entity battleUserData = this.getBattleDataEntry(battlename, robot);
		if (battleUserData != null) {
			return "robot already exist";
		}
		String subkey = "udata" + battlename + robot;
		battleUserData = new Entity("BattleData", subkey);
		battleUserData.setProperty("bBattle", battlename);
		battleUserData.setProperty("bUser", user);
		battleUserData.setProperty("bRobot", robot);
		datastore.put(battleUserData);

		/*
		 * Update number of players
		 */
		
		battleEntity.setProperty("players", player);
		level = this.getSessionLevel(battlename, robot);
		battleEntity.setProperty("level", level);
		datastore.put(battleEntity);
		buff.append("Added robot");
		buff.append(robot);
		buff.append("player");
		buff.append(player);
		buff.append("\n ");

		/*
		 * If sufficient number of players are in, start the battle
		 */
		System.out.println("Players" + player + "Max P" + maxplayers);
		if (player.longValue() == maxplayers.longValue()) {
			/*scores = this.startBattle(battlename);
			System.out.println("start battle");
			for (RoboScores score: scores) {
				buff.append("Robot ");
				buff.append(score.getRobot());
				buff.append(",");
				buff.append("Score ");
				buff.append(score.getScore());
				buff.append("\n");
			}
			return buff.toString();*/
			//String robos = roboServ.startBattleRobots(battlename);
			return constructCall(battlename,roboServ);
			//TODO start robobattle code
			//return new String("http://www.yahoo.com");
			//return new String("http://"+BackendServiceFactory.getBackendService().getBackendAddress("example")+"startgame");
		}
		//return buff.toString();
		return null;
	}

	String constructCall(String battlename,RobotServices roboServ) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        SessionServices s = new SessionServices();        
        Entity battle = s.getBattleEntry(battlename);
        Long rounds =(Long) battle.getProperty("rounds");
        String robots = roboServ.startBattleRobots(battlename);
        StringBuffer buff = new StringBuffer();
        buff.append("http://");
        buff.append(BackendServiceFactory.getBackendService().getBackendAddress("example"));
        buff.append("/startgame?robots=");
        buff.append(robots);
        buff.append("&battle=");
        buff.append(battlename);
        buff.append("&round=");
        buff.append(rounds);
        //String base = new String("http://"+BackendServiceFactory.getBackendService().getBackendAddress("example")+"/startgame"+"?robots="+robots+"&battle="+battlename+"&round="+rounds);
        String base = buff.toString();
        System.out.println("game url: "+base);
        battle.setProperty("state", "Done");
        datastore.put(battle);
        return(base);
    }
	
	Entity getBattleDataEntry(String battlename, String robot)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String subkey = "udata" + battlename + robot;

		Key key = KeyFactory.createKey("BattleData", subkey);

		try {
			Entity battleEntity = datastore.get(key);
			return(battleEntity);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return(null);
		} 

	}
	
	Entity getBattleEntry(String battlename)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("Battle", battlename);

		try {
			Entity battleEntity = datastore.get(key);
			return(battleEntity);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return(null);
		} 

	}
	
	String deleteSessionData(String battlename, String robot) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity session = this.getBattleEntry(battlename);
		
		/*
		 * Check user permissions
		 */
		RoboUserService rUserService = new RoboUserService();
		String user = rUserService.getUserName();
		String usr = (String)session.getProperty("bUser");
		if (usr.equals(user) == false) {
			return ("No permission to delete" + robot + "for user" + user);
		}
		
		Long players = (Long) session.getProperty("players");
		players = players - 1;
		session.setProperty("players", players);
		datastore.put(session);


		Entity sessionData = this.getBattleDataEntry(battlename, robot);
		if (sessionData == null) {
			return("Entry not found for " + battlename + robot);
		}
		datastore.delete(sessionData.getKey());
		return("Deleted " + battlename + robot);
	}

	String deleteSession(String battlename) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		StringBuffer buff = new StringBuffer();
		String robot;

		Entity battle = this.getBattleEntry(battlename);
		if (battle == null) {
			return("Battle " + battle + "not found");
		}

		/*
		 * Check user permissions
		 */
		RoboUserService rUserService = new RoboUserService();
		String user = rUserService.getUserName();
		String usr = (String)battle.getProperty("user");
		if (usr.equals(user) == false) {
			return ("No permission to delete" + battlename + "for user" + user);
		}
		
		datastore.delete(battle.getKey());

		/*
		 * Delete associated battle data
		 */
		Query gaeQuery = new Query("BattleData");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		buff.append("Deleting" );
		for (Entity entity : list) {
			if (battlename.equals(entity.getProperty("bBattle")) == false) {
				continue;
			}
			robot = (String)entity.getProperty("bRobot");
			buff.append(robot);
			buff.append(",");
			this.deleteSessionData(battlename, robot);
		}
		return buff.toString();
	}

	String deleteAllSessions () {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query gaeQuery = new Query("Battle");
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		 StringBuffer buff = new StringBuffer();

		 buff.append("Deleting" );
		 for (Entity entity : list) {
			 buff.append(entity.getProperty("name"));
			 buff.append(" ");
			 Key key = entity.getKey();
			 datastore.delete(key);
		 }
		 return buff.toString();
	}	

	String deleteAllSessionsData () {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query gaeQuery = new Query("BattleData");
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		 StringBuffer buff = new StringBuffer();

		 buff.append("Deleting" );
		 for (Entity entity : list) {
			 buff.append(entity.getProperty("name"));
			 buff.append(" ");
			 Key key = entity.getKey();
			 datastore.delete(key);
		 }
		 return buff.toString();
	}	

	String getSessionData() {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query gaeQuery = new Query("Battle");
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		 StringBuffer buff = new StringBuffer();
		 Long players;
		 String battlename;
		 Long timeout = (long)(10 * 60 * 60 * 1000);

		 for (Entity entity : list) {
			 
			 Date d = (Date)entity.getProperty("starttime");
			 Date present = new Date();
			 
			 if ((present.getTime() - d.getTime()) > timeout) {
				 buff.append("Skipping the ");
				 buff.append(entity.getProperty("name"));
				 buff.append("\n");
				 continue;
			 }
			 
			 buff.append("Battle Name: ");
			 battlename = (String)entity.getProperty("name");
			 buff.append(battlename);
			 buff.append(",");
			 buff.append("Players: ");
			 players = (Long)entity.getProperty("players");
			 buff.append(players);
			 buff.append(",");
			 buff.append("Max Players: ");
			 buff.append(entity.getProperty("maxplayers"));
			 buff.append(",");
			 buff.append("Level: ");
			 buff.append(entity.getProperty("level"));
			 buff.append(",");
			 buff.append("Rounds: ");
			 buff.append(entity.getProperty("rounds"));
			 buff.append(",");
			 buff.append("Start Time:");
			 buff.append(entity.getProperty("starttime"));
			 buff.append("\n");
			 Query gaesubQuery = new Query("BattleData");
			 PreparedQuery subpq = datastore.prepare(gaesubQuery);
			 List<Entity> sublist = subpq.asList(FetchOptions.Builder.withDefaults());
			 for (Entity subentity : sublist) {
				 if (battlename.equals(subentity.getProperty("bBattle")) == false) {
					 continue;
				 }
				 buff.append("bUsres ");
				 buff.append(subentity.getProperty("bUser"));
				 buff.append(",");
				 buff.append("bRobot ");
				 buff.append(subentity.getProperty("bRobot"));
				 buff.append(",");
				 buff.append("Battle ");
				 buff.append(subentity.getProperty("bBattle"));
				 buff.append("\n");
			 }
		 }

		 return buff.toString();
	}
	
	String setSessionFilter(Long level, Long maxplayers) {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Filter levelFilter = new FilterPredicate("level",
				              FilterOperator.GREATER_THAN_OR_EQUAL,
				              level);
		 
		 Filter maxPlayerFilter = new FilterPredicate("maxplayers",
	              FilterOperator.GREATER_THAN_OR_EQUAL,
	              maxplayers);
		 
		 Query gaeQuery = new Query("Battle").setFilter(levelFilter).setFilter(maxPlayerFilter);
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 StringBuffer buff = new StringBuffer();
		 boolean first = true;
	        for (Entity entity : pq.asIterable()) {
	        	if(!first){
	        		buff.append(",");	
	        	}
	            buff.append((String)entity.getProperty("name"));            
	            first= false;
	        }
		return buff.toString();
	}
	
	String getSessionEntries(String battlename) {
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 String subkey = "udata"+ battlename;

		 Query gaeQuery = new Query(subkey);
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		 StringBuffer buff = new StringBuffer();

		 for (Entity entity : list) {
			 buff.append("bUsres ");
			 buff.append(entity.getProperty("bUser"));
			 buff.append(",");
			 buff.append("bRobot ");
			 buff.append(entity.getProperty("bRobot"));
			 buff.append(",");
			 buff.append("bUsres ");
			 buff.append(entity.getProperty("position"));
			 buff.append("\n");
		 }
		 return buff.toString();
	}
	
	String startBattleAdaptor(String battlename)
	{
		List<RoboScores> scores;
		StringBuffer buff = new StringBuffer();
		RobotServices robser = new RobotServices();
		
		 scores = this.startBattle(battlename);
		 for (RoboScores score: scores) {
			 buff.append("Robot ");
			 buff.append(score.getRobot());
			 buff.append(",");
			 buff.append("Score ");
			 buff.append(score.getScore());
			 buff.append("\n");
		 }
		 
		 robser.updateStats(scores);
		 return buff.toString();
	}
	
	List<RoboScores> startBattle(String battlename) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<String> battleRobots = new ArrayList<String>();
		List<RoboScores> scores;
		RoboCodeAdaptor roboAdaptor = new RoboCodeAdaptor();
		RobotServices roboservice = new RobotServices();
		
		Query gaeQuery = new Query("BattleData");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		/*
		 * Create array list of robots
		 */
		for (Entity entity : list) {
			/*
			 * Subtract user points
			 */
			Long cut;
			RoboUserService rUserService = new RoboUserService();
			Entity user = rUserService.getUserEntityByName((String)entity.getProperty("bUser"));
			Long points = (Long)user.getProperty("points");
			RobotServices rService = new RobotServices();
			Entity robo = rService.getRobot((String)entity.getProperty("bRobot"));
			Long roboLevel = (Long)robo.getProperty("level");
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
			battleRobots.add((String)entity.getProperty("bRobot"));
		}
		
		/*
		 * run battle and rank them
		 */
		scores = roboAdaptor.runBattle(battleRobots);
		scores = roboservice.rankRobots(scores);
		scores = roboservice.levelRobots(scores, battlename);
		return(scores);
	}	
	
	Long getSessionLevel(String battlename, String robo) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaesubQuery = new Query("BattleData");
		PreparedQuery subpq = datastore.prepare(gaesubQuery);
		List<Entity> sublist = subpq.asList(FetchOptions.Builder.withDefaults());
		Long level = (long)0;
		Long count = (long)0;
		RobotServices robotServ = new RobotServices();
		Entity robot = null;
		
		if (robo != null) {
			robot = robotServ.getRobot(robo);
			level = level + (Long)robot.getProperty("level");
			count++;
		}
		
		for (Entity subentity : sublist) {
			if (battlename.equals(subentity.getProperty("bBattle")) == false) {
				continue;
			}
			count++;
			robot = robotServ.getRobot((String)subentity.getProperty("bRobot"));  
			level = level + (Long)robot.getProperty("level");
		}
		if (count == 0) {
			return((long)0);
		}
		return(level/count);
	}
	
	String getUserBattles() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query gaesubQuery = new Query("BattleData");
        PreparedQuery subpq = datastore.prepare(gaesubQuery);
        List<Entity> sublist = subpq.asList(FetchOptions.Builder.withDefaults());
        StringBuffer buff = new StringBuffer();
        UserService userService = UserServiceFactory.getUserService();
        User usr = userService.getCurrentUser();
        boolean var = false;
        for (Entity subentity : sublist) {
            String user = (String)subentity.getProperty("bUser");
            if (user.equals(usr.getEmail()) == false) {
                continue;
            }
            if (var == true) {
                buff.append(",");
            }
            var = true;
            buff.append(subentity.getProperty("bBattle"));
            buff.append(":");
            buff.append(subentity.getProperty("bRobot"));
        }
        return buff.toString();
   }
	
	String loadResult(String battlename)
    {
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            StringBuffer buff = new StringBuffer();

            Key key = KeyFactory.createKey("Score", battlename);

            try {
                    Entity battleEntity = datastore.get(key);
                    Map<String, Object> properties = battleEntity.getProperties();
                    boolean var = true;
                    for (Map.Entry<String,Object> item : properties.entrySet()) {
                            if(item.getValue() == null) continue;
                            else{
                                    if (var == false) {
                                            buff.append(",");
                                    }
                                    var = true;
                                    buff.append(item.getKey());
                                    buff.append(":");
                                    buff.append(item.getValue());
                                    buff.append("\n");
                            }
                    }
                    return(buff.toString());
            } catch (EntityNotFoundException e) {
                    // TODO Auto-generated catch block
                    return("Not found");
            } 

    }
	
	BattleData getBattleData(String battlename) {
		BattleData battle = new BattleData();
		ArrayList<BattleSessionData> bdata = new ArrayList<BattleSessionData>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity bat = getBattleEntry(battlename);
		battle.setBattle_name(battlename);
		battle.setLevel((Long)bat.getProperty("level"));
		battle.setMaxplayers((Long)bat.getProperty("maxplayers"));
		battle.setPlayers((Long)bat.getProperty("players"));
		battle.setRounds((Long)bat.getProperty("rounds"));
		battle.setState((String)bat.getProperty("state"));
		battle.setUser((String)bat.getProperty("user"));

		Query gaesubQuery = new Query("BattleData");
		PreparedQuery subpq = datastore.prepare(gaesubQuery);
		List<Entity> sublist = subpq.asList(FetchOptions.Builder.withDefaults());
		for (Entity subentity : sublist) {
			if (battlename.equals(subentity.getProperty("bBattle")) == false) {
				continue;
			}
			BattleSessionData bs = new BattleSessionData();
			bs.setRobot((String)subentity.getProperty("bRobot"));
			//bs.setScore((long)0);
			bs.setScore(getScore(battlename, (String)subentity.getProperty("bRobot")));
			bs.setUser((String)subentity.getProperty("bUser"));
			bdata.add(bs);
		}
		battle.setBdata(bdata);
		return(battle);
	}
	
	Long getScore(String battlename, String robot)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("Score", battlename);

		try {
			Entity battleEntity = datastore.get(key);
			Map<String, Object> properties = battleEntity.getProperties();
			for (Map.Entry<String,Object> item : properties.entrySet()) {
				if (robot.equals(item.getKey()) == true) {
					return((Long)item.getValue());
				}
			}

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return((long)0);
		}
		return((long)0);
	}
}
