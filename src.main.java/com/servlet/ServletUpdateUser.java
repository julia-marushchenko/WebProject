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

public class ServletUpdateUser extends HttpServlet {

	private static final long serialVersionUID = 9061781212752796041L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int userID = Integer.parseInt(req.getParameter("id"));
		UserDaoImpl userImpl = new UserDaoImpl();
		User user = null;
		try {
			user = userImpl.read(userID);

		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		req.setAttribute("user", user);

		MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
		Set<MusicType> setMusic = musicTypes.read();
		req.setAttribute("musicTypes", setMusic);
		RoleDaoImpl role = new RoleDaoImpl();
		Set<Role> setRoles = role.read();
		req.setAttribute("roles", setRoles);
		req.getRequestDispatcher("updateUserPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int userID = Integer.parseInt(req.getParameter("id"));
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		int age = Integer.parseInt(req.getParameter("age"));
		String country = req.getParameter("country");
		String street = req.getParameter("street");
		int zipCode = Integer.parseInt(req.getParameter("zipCode"));
		String roleSite = req.getParameter("Role");
		String[] musics = req.getParameterValues("musicTypes");

		boolean updated = false;
		UserDaoImpl userImpl = new UserDaoImpl();

		if (!login.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && age != 0
				&& !country.isEmpty() && !street.isEmpty() && zipCode != 0 && musics!=null) {

			User user = new User();
			user.setId(userID);
			user.setLogin(login);
			user.setPassword(password);
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
			updated = userImpl.update(user);

		}
		if (updated) {
			Set<User> setUsers = userImpl.read();
			req.setAttribute("setUsers", setUsers);
			req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
		} else {
			String message = "Empty fields";
			User user = null;
			try {
				user = userImpl.read(userID);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			req.setAttribute("user", user);
			req.setAttribute("errorMessage", message);
			MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
			Set<MusicType> setMusic = musicTypes.read();
			req.setAttribute("musicTypes", setMusic);
			RoleDaoImpl role = new RoleDaoImpl();
			Set<Role> setRoles = role.read();
			req.setAttribute("roles", setRoles);
			req.getRequestDispatcher("updateUserPage.jsp").forward(req, resp);
		}
	}

}
