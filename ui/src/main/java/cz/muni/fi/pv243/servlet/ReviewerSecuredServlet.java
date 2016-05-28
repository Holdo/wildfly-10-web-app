package cz.muni.fi.pv243.servlet;

import javax.annotation.security.DeclareRoles;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Michal Holic on 28/04/2016
 */
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "reviewer"}))
@DeclareRoles({"admin", "reviewer"})
@WebServlet(name = "ReviewerSecuredServlet", urlPatterns = {"/secured/reviewer/"}, loadOnStartup = 1)
public class ReviewerSecuredServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(ReviewerSecuredServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getUserPrincipal().getName();
		resp.addCookie(new Cookie("username", username));
		req.getRequestDispatcher("index.html").forward(req, resp);
	}
}
