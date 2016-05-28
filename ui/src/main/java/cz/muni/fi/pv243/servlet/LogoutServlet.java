package cz.muni.fi.pv243.servlet;

import javax.annotation.security.DeclareRoles;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Michal Holic on 23/05/2016
 */
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "reviewer"}))
@DeclareRoles({"admin", "reviewer"})
@WebServlet(name = "LogoutServlet", urlPatterns = {"/secured/logout"}, loadOnStartup = 1)
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.logout();
		resp.sendRedirect("/");
	}
}
