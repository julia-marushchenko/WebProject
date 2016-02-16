package com.daoImpl;

import java.sql.*;
import java.util.*;
import com.dao.Dao;
import com.java.NotFoundException;
import com.java.Role;

public class RoleDaoImpl implements Dao<Role> {

	private final static String CREATE_ROLE = "INSERT INTO role (role_name) VALUES(?)";
	private final static String SHOW_ROLE = "SELECT * FROM role";
	private final static String SHOW_ROLE_WITH_ID = "SELECT * FROM role WHERE id_role=?";
	private final static String UPDATE_ROLE = "UPDATE role SET role_name=? WHERE id_role=?";
	private final static String DELETE_ROLE = "DELETE FROM role WHERE id_role=?";

	
	@Override
	public Role create(Role role) {

		Connection con = ConnectionDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(CREATE_ROLE, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, role.getRoleName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int idRes = rs.getInt(1);
				role.setId(idRes);
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;

	}

	@Override
	public Set<Role> read() {
		Set<Role> set = new HashSet<>();

		Connection con = ConnectionDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(SHOW_ROLE);

			ResultSet res = ps.executeQuery();
			while (res.next()) {
				int id = res.getInt("id_role");
				String roleName = res.getString("role_name");
				Role newRole = new Role();
				newRole.setId(id);
				newRole.setRoleName(roleName);
				set.add(newRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Role read(int id) throws NotFoundException {

		Connection con = ConnectionDB.getConnection();
		Role role = new Role();
		try {
			PreparedStatement pr = con.prepareStatement(SHOW_ROLE_WITH_ID);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			if (!res.next()) {
				throw new NotFoundException("No user with this ID");
			}
			role.setId(id);
			role.setRoleName(res.getString("role_name"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public boolean update(Role obj) {
		Connection con = ConnectionDB.getConnection();
		boolean updated = false;
		try {
			PreparedStatement ps = con.prepareStatement(UPDATE_ROLE);
			ps.setString(1, obj.getRoleName());
			ps.setInt(2, obj.getId());
			int update = ps.executeUpdate();
			if (update == 1) {
				updated = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;

	}

	@Override
	public boolean delete(int id) {
		Connection con = ConnectionDB.getConnection();
		boolean deleted = false;
		try {
			PreparedStatement ps = con.prepareStatement(DELETE_ROLE);
			ps.setInt(1, id);
			int i = ps.executeUpdate();
			if (i == 1) {
				deleted = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deleted;

	}
	


}
