package com.robo.service.rest.impl;

public class Robots {
	String Robots;
	String user;
	boolean shared;
	Long score;
	Long games;
	Long level;
	Long rank;
	
	public String getRobots() {
		return Robots;
	}
	public void setRobots(String robots) {
		Robots = robots;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Long getGames() {
		return games;
	}
	public void setGames(Long games) {
		this.games = games;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}

}
