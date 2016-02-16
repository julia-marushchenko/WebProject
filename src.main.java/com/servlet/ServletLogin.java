package com.servlet;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoImpl.UserDaoImpl;
import com.java.Role;
import com.java.User;

public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = -6919155288744593738L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String login = req.getParameter("login");
		String pass = req.getParameter("password");
		UserDaoImpl userImpl = new UserDaoImpl();
		Set<User> existUsers = userImpl.read();
		boolean isExist = false;
		Role role = new Role();
		String name =  null;
		String lastName = null;
		for (User user : existUsers) {
			if (user.getLogin().equals(login) && user.getPassword().equals(pass)) {
				isExist = true;
				role = user.getRole();
				name =  user.getFirstName();
				lastName = user.getLastName();
			}
		}
		if (isExist) {
			if (role.getRoleName().equals("ADMIN")) {
				HttpSession se = req.getSession(true);
				se.setAttribute("login", login);	
				Set<User> setUsers = userImpl.read();
				req.setAttribute("setUsers", setUsers);				
				req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
				
			} else if (role.getRoleName().equals("MODERATOR")) {
				HttpSession se = req.getSession(true);
				se.setAttribute("login", login);		
				Set<User> setUsers = userImpl.read();
				req.setAttribute("setUsers", setUsers);		
				req.getRequestDispatcher("moderatorPage.jsp").forward(req, resp);
				
			} else {
				HttpSession se = req.getSession(true);
				se.setAttribute("login", login);
				se.setAttribute("name", name);
				se.setAttribute("lastName", lastName);
				req.getRequestDispatcher("welcome.jsp").forward(req, resp);
			}

		} else {
			String message = "Incorrect login or password";
			req.setAttribute("errorMessage", message);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}

	}

}
