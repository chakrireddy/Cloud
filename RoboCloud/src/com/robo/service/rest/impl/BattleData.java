package com.robo.service.rest.impl;

import java.util.ArrayList;

public class BattleData {
	String Battle_name;
	String user;
	Long players;
	Long maxplayers;
	Long level;
	Long rounds;
	String state;
	ArrayList<BattleSessionData> bdata = new ArrayList<BattleSessionData>();
	
	public String getBattle_name() {
		return Battle_name;
	}
	public void setBattle_name(String battle_name) {
		Battle_name = battle_name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getPlayers() {
		return players;
	}
	public void setPlayers(Long players) {
		this.players = players;
	}
	public Long getMaxplayers() {
		return maxplayers;
	}
	public void setMaxplayers(Long maxplayers) {
		this.maxplayers = maxplayers;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public Long getRounds() {
		return rounds;
	}
	public void setRounds(Long rounds) {
		this.rounds = rounds;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public ArrayList<BattleSessionData> getBdata() {
		return bdata;
	}
	public void setBdata(ArrayList<BattleSessionData> bdata) {
		this.bdata = bdata;
	}
}
