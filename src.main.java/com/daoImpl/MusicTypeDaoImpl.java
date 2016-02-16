package com.daoImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import com.dao.Dao;
import com.java.MusicType;
import com.java.NotFoundException;

public class MusicTypeDaoImpl implements Dao<MusicType> {
	private final static String CREATE_MUSIC_TYPE = "INSERT INTO music_type(type_name) VALUES (?)";
	private final static String SHOW_MUSIC_TYPES = "SELECT * FROM music_type";
	private final static String SHOW_MUSIC_TYPE_BY_ID = "SELECT * FROM music_type WHERE id_music=?";
	private final static String UPDATE_MUSIC_TYPE = "UPDATE music_type SET type_name=? WHERE id_music=?";
	private final static String DELETE_MUSIC_TYPE = "DELETE FROM music_type WHERE id_music=?";

	

	@Override
	public MusicType create(MusicType obj) {
		Connection con = ConnectionDB.getConnection();
		try {
			PreparedStatement pr = con.prepareStatement(CREATE_MUSIC_TYPE, Statement.RETURN_GENERATED_KEYS);
			pr.setString(1, obj.getTypeName());
			pr.executeUpdate();
			ResultSet rs = pr.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				obj.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	@Override
	public Set<MusicType> read() {
		Connection con = ConnectionDB.getConnection();
		Set<MusicType> set = new HashSet<>();
		try {
			PreparedStatement pr = con.prepareStatement(SHOW_MUSIC_TYPES);
			ResultSet res = pr.executeQuery();
			while (res.next()) {
				MusicType mt = new MusicType();
				mt.setId(res.getInt("id_music"));
				mt.setTypeName(res.getString("type_name"));
				set.add(mt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	@Override
	public MusicType read(int id) throws NotFoundException {
		Connection con = ConnectionDB.getConnection();
		MusicType mt = new MusicType();
		try {
			PreparedStatement pr = con.prepareStatement(SHOW_MUSIC_TYPE_BY_ID);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			if (!res.next()) {
				throw new NotFoundException("No user with this ID");
			}
			mt.setTypeName(res.getString("type_name"));
			mt.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mt;
	}

	@Override
	public boolean update(MusicType obj) {
		Connection con = ConnectionDB.getConnection();
		boolean updated = false;
		try {
			PreparedStatement ps = con.prepareStatement(UPDATE_MUSIC_TYPE);
			ps.setString(1, obj.getTypeName());
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
			PreparedStatement ps = con.prepareStatement(DELETE_MUSIC_TYPE);
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
