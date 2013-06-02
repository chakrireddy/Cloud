package com.robo.service.rest.impl;

public class BattleSessionData {
	String robot;
	String user;
	Long score;
	public String getRobot() {
		return robot;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setRobot(String robot) {
		this.robot = robot;
	}
	public String getUser() {
		return user;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}

}
