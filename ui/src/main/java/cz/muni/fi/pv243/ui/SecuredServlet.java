package cz.muni.fi.pv243.ui;

import javax.annotation.security.DeclareRoles;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Michal Holic on 28/04/2016
 */
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "artist", "reviewer"}))
@DeclareRoles({"admin", "artist", "reviewer"})
@WebServlet(name = "SecuredServlet", urlPatterns = {"/secured"}, loadOnStartup = 1)
public class SecuredServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SecuredServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.isUserInRole("admin")) {
			log.info("Admin logged in.");
			resp.sendRedirect("/secured/admin/index.html");
		}
		else if (req.isUserInRole("reviewer")) {
			log.info("Reviewer logged in.");
			resp.sendRedirect("/secured/reviewer/index.html");
		}
		else  {
			log.warning(req.getUserPrincipal().getName() + " failed to log in.");
			resp.sendRedirect("/403.html");
		}
	}
}
