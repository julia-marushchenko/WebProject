package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletLogout extends HttpServlet{

	private static final long serialVersionUID = -8932886996702827483L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.removeAttribute("login");
		/*req.removeAttribute("name");
		req.removeAttribute("lastName");*/
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	

}
