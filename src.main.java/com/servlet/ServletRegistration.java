package com.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoImpl.MusicTypeDaoImpl;
import com.daoImpl.UserDaoImpl;
import com.java.Address;
import com.java.MusicType;
import com.java.NotFoundException;
import com.java.Role;
import com.java.User;

public class ServletRegistration extends HttpServlet {

	private static final long serialVersionUID = 7273469736303100097L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
		Set<MusicType> setMusic = musicTypes.read();
		req.setAttribute("musicTypes", setMusic);
		req.getRequestDispatcher("registration.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String confPassword = req.getParameter("confirmPassword");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		int age = Integer.parseInt(req.getParameter("age"));
		String country = req.getParameter("country");
		String street = req.getParameter("street");
		int zipCode = Integer.parseInt(req.getParameter("zipCode"));
		String[] musics = req.getParameterValues("musicTypes");
		Set<MusicType> set = new HashSet<>();
		MusicTypeDaoImpl musicImpl = new MusicTypeDaoImpl();
		for (String el : musics) {
			int id = Integer.parseInt(el);
			try {
				set.add(musicImpl.read(id));
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}

		if (!login.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && age != 0
				&& !country.isEmpty() && !street.isEmpty() && zipCode != 0 && !confPassword.isEmpty()) {

			UserDaoImpl userCheck = new UserDaoImpl();
			Set<User> usersSet = userCheck.read();
			boolean isExist = false;
			for (User userr : usersSet) {
				if (userr.getLogin().equals(login)) {
					isExist = true;
				}				
			}
			if (!isExist && password.equals(confPassword)) {
				User user = new User();
				user.setLogin(login);
				user.setPassword(confPassword);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setAge(age);
				Address address = new Address();
				address.setCountry(country);
				address.setStreet(street);
				address.setZipCode(zipCode);
				user.setAddress(address);
				user.setMusicTypes(set);
				Role role = new Role();
				role.setRoleName("USER");
				user.setRole(role);
				UserDaoImpl userImpl = new UserDaoImpl();
				user = userImpl.create(user);
				HttpSession se = req.getSession(true);
				se.setAttribute("login", login);
				se.setAttribute("name", firstName);
				se.setAttribute("lastName", lastName);
				req.getRequestDispatcher("welcome.jsp").forward(req, resp);
			} else {
				String message = "Login is not available or wrong confirmation of password";
				req.setAttribute("errorMessage", message);
				req.getRequestDispatcher("registration.jsp").forward(req, resp);
			}
		}

	}

}
