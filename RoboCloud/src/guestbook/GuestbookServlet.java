package guestbook;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.*;

import robocode.Robocode;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.robo.service.rest.impl.SessionServices;

public class GuestbookServlet extends HttpServlet {
	Logger log = Logger.getLogger(GuestbookServlet.class.getName());
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (user != null) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello, " + user.getNickname());
        } else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
        
        /*URL url = null;
		try {
			log.info("Backendurl: "+BackendServiceFactory.getBackendService().getBackendAddress("example"));
			url = new URL("http://example.robowarsj.appspot.com/guestbook");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			log.info(e.getMessage());
		} catch (IOException e) {
			log.info(e.getMessage());
		}*/
        //Robocode.runRoboCode(new String[0]);
        //resp.sendRedirect("http://"+BackendServiceFactory.getBackendService().getBackendAddress("example")+"/rest/robot/rtask");
/*        URL url = null;
		try {
			log.info("Backendurl: "+BackendServiceFactory.getBackendService().getBackendAddress("example"));
//			url = new URL("http://example.robowarsj.appspot.com/rest/robot/rtask");
			url = new URL("http://"+BackendServiceFactory.getBackendService().getBackendAddress("example")+"/rest/robot/rtask");
			//url = new URL("http://example.robowarsj.appspot.com/guestbook");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
		} catch (MalformedURLException e) {
			log.info(e.getMessage());
		} catch (IOException e) {
			log.info(e.getMessage());
		} */
		//String battlename = req.getParameter("battlename");
		//SessionServices session = new  SessionServices();
		 //return session.joinBattle(battlename, robot);
       // log.info("guestbookservlet running");
        //resp.sendRedirect("http://"+BackendServiceFactory.getBackendService().getBackendAddress("example")+"startgame");        
    }
}