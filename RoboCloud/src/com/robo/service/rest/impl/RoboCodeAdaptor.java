package com.robo.service.rest.impl;

import java.util.ArrayList;
import java.util.List;

public class RoboCodeAdaptor {
	List<RoboScores> runBattle(List<String> robots) {
		Long l = (long)100;
		ArrayList<RoboScores> scores = new ArrayList<RoboScores>();
		for (String rob: robots) {
			RoboScores score = new RoboScores();
			score.setRobot(rob);
			score.setScore(l);
			scores.add(score);
			l++;
		}
		return(scores);
	}
}
