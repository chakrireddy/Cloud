package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import robocode.Robocode;

public class StartGame extends HttpServlet{
Logger log = Logger.getLogger(StartGame.class.getName());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doService(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doService(req, resp);
	}
	
	protected void doService(HttpServletRequest req, HttpServletResponse resp){
		log.info("Gamestarted");
		String robots = req.getParameter("robots");
		String battlename = req.getParameter("battle");
		String rounds = req.getParameter("round");
		int round = 10;
		if(rounds != null){
			round = Integer.parseInt(rounds);
		}
		Robocode.runRoboCode(new String[0],robots,battlename,round);
		try {
			//resp.sendRedirect("page to show game is running");
			resp.getWriter().println("code finished");
			
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		try {
			resp.sendRedirect("http://robowarsj.appspot.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
