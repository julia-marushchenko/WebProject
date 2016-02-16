package com.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.daoImpl.MusicTypeDaoImpl;
import com.daoImpl.RoleDaoImpl;
import com.daoImpl.UserDaoImpl;
import com.java.Address;
import com.java.MusicType;
import com.java.NotFoundException;
import com.java.Role;
import com.java.User;

public class ServletAddUserPage extends HttpServlet{

	private static final long serialVersionUID = -6928745083920725371L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
		Set<MusicType> setMusic = musicTypes.read();
		req.setAttribute("musicTypes", setMusic);
		RoleDaoImpl role = new RoleDaoImpl();
		Set<Role> setRoles = role.read();
		req.setAttribute("roles", setRoles);
		req.getRequestDispatcher("addUserPage.jsp").forward(req, resp);
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
		String roleSite = req.getParameter("Role");
		String[] musics = req.getParameterValues("musicTypes");
		

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
				Role role = new Role();
				role.setRoleName(roleSite);
				user.setRole(role);
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
				user.setMusicTypes(set);
				UserDaoImpl userImpl = new UserDaoImpl();
				user = userImpl.create(user);
				Set<User> setUsers = userImpl.read();
				req.setAttribute("setUsers", setUsers);	
				req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
			} else {
				String message = "Login is not available or wrong confirmation of password";
				req.setAttribute("errorMessage", message);
				req.getRequestDispatcher("addUserPage.jsp").forward(req, resp);
			}
		}
	}

	

}
