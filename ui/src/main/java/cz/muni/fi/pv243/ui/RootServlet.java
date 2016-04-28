package cz.muni.fi.pv243.ui;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Michal Holic on 28/04/2016
 */
@WebServlet(name = "RootServlet", urlPatterns = {"/root"}, loadOnStartup = 1)
public class RootServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().print("We're in business!");
	}
}
