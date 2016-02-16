package com.daoImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import com.dao.Dao;
import com.java.Address;
import com.java.MusicType;
import com.java.NotFoundException;
import com.java.Role;
import com.java.User;

public class UserDaoImpl implements Dao<User> {
	private final static String CREATE_USER = "INSERT INTO user (login, password, first_name, last_name, age)"
			+ " VALUES (?,?,?,?,?)";
	private final static String SET_ADDRESS_ID = "UPDATE user SET address_id=? WHERE id=?";
	private final static String SET_ROLE_ID = "UPDATE user SET role_id=? WHERE id=?";
	private final static String SET_MUSIC_ID_USER_ID = "INSERT INTO user_musictype (user_id, music_type_id)"
			+ "VALUES(?,?)";
	private final static String READ_USERS = "SELECT id, login, password, first_name, last_name, age,"
			+ " id_address, country, street, zip, id_role, role_name, type_name, id_music FROM user,"
			+ " address, music_type, role, user_musictype WHERE user.id=address.id_address AND "
			+ "user.role_id=role.id_role AND user.id=user_musictype.user_id AND"
			+ " user_musictype.music_type_id=music_type.id_music;";
	private final static String READ_USER_BY_ID = "SELECT * FROM user WHERE id=?";
	private final static String GET_USER_ROLE = "SELECT id_role, role_name FROM role ,user WHERE"
			+ " user.id = ? AND role_id=id_role";
	private final static String GET_MUSIC_BY_USER_ID = "SELECT id_music, type_name FROM music_type, user,"
			+ " user_musictype WHERE user.id=? AND user_id=id AND music_type_id=id_music;";
	private final static String UPDATE_USER = "UPDATE user SET login=?, password=?, first_name=?, last_name=?, age=?, role_id=? WHERE id=?";
	private final static String UPDATE_ADDRESS = "UPDATE address SET country=?, street=?, zip=? WHERE id_address=?";
	private final static String DELETE_OLD_MUSIC_ID_USER_ID = "DELETE FROM user_musictype WHERE user_id=?";
	private final static String INSERT_NEW_MUSIC_ID_USER_ID = "INSERT INTO user_musictype (user_id, music_type_id)"
			+ "VALUES (?,?)";
	private final static String DELETE_USER = "DELETE FROM user WHERE id=?";
	private final static String DELETE_USER_MUSIC_SET = "DELETE FROM user_musictype WHERE user_id=?";

	


	@Override
	public User create(User user) {

		Connection con = ConnectionDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setInt(5, user.getAge());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
				user.setId(id);
			}
			AddressDaoImpl addImpl = new AddressDaoImpl();
			Address address = new Address();
			address = user.getAddress();
			address.setUser(user);
			addImpl.create(address);
			PreparedStatement prStAdd = con.prepareStatement(SET_ADDRESS_ID);
			prStAdd.setInt(1, id);
			prStAdd.setInt(2, id);
			prStAdd.executeUpdate();
			RoleDaoImpl role = new RoleDaoImpl();
			Set<Role> setRole = new HashSet<>();
			setRole.addAll(role.read());
			for (Role roleSet : setRole) {
				if (roleSet.getRoleName().equals(user.getRole().getRoleName())) {
					PreparedStatement prSt = con.prepareStatement(SET_ROLE_ID);
					prSt.setInt(1, roleSet.getId());
					prSt.setInt(2, id);
					prSt.executeUpdate();
					Role newRole = new Role();
					newRole.setId(roleSet.getId());
					newRole.setRoleName(roleSet.getRoleName());
					user.setRole(newRole);
				}
			}
			Set<MusicType> setMusicUser = new HashSet<>();
			setMusicUser.addAll(user.getMusicTypes());
			Set<MusicType> setMusicBD = new HashSet<>();
			MusicTypeDaoImpl musicImpl = new MusicTypeDaoImpl();
			setMusicBD.addAll(musicImpl.read());
			for (MusicType userMusic : setMusicUser) {
				for (MusicType bdMusic : setMusicBD) {
					if (userMusic.getTypeName().equals(bdMusic.getTypeName())) {
						PreparedStatement prStat = con.prepareStatement(SET_MUSIC_ID_USER_ID);
						prStat.setInt(1, id);
						prStat.setInt(2, bdMusic.getId());
						prStat.executeUpdate();
						userMusic.setId(bdMusic.getId());
						user.setMusicTypes(setMusicUser);

					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

	@Override
	public Set<User> read() {
		Set<User> set = new HashSet<>();
		Connection con = ConnectionDB.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(READ_USERS);
			ResultSet res = ps.executeQuery();

			while (res.next()) {
				boolean flag = false;
				Iterator<User> iterator = set.iterator();
				while (iterator.hasNext()) {
					User iterUser = iterator.next();
					if (iterUser.getId() == res.getInt("id")) {
						flag = true;
						set.remove(iterUser);
						User user = new User();
						user.setId(iterUser.getId());
						user.setLogin(iterUser.getLogin());
						user.setPassword(iterUser.getPassword());
						user.setFirstName(iterUser.getFirstName());
						user.setLastName(iterUser.getLastName());
						user.setAge(iterUser.getAge());
						user.setAddress(iterUser.getAddress());
						user.setRole(iterUser.getRole());
						MusicType music = new MusicType();
						Set<MusicType> setMusic = new HashSet<>();
						setMusic.addAll(iterUser.getMusicTypes());
						music.setTypeName(res.getString("type_name"));
						music.setId(res.getInt("id_music"));
						setMusic.add(music);
						user.setMusicTypes(setMusic);
						set.add(user);
						break;
					}

				}
				if (flag) {
					continue;
				}

				User user = new User();
				Address address = new Address();
				Role role = new Role();
				MusicType music = new MusicType();
				Set<MusicType> setMusic = new HashSet<>();
				user.setId(res.getInt("id"));
				user.setLogin(res.getString("login"));
				user.setPassword(res.getString("password"));
				user.setFirstName(res.getString("first_name"));
				user.setLastName(res.getString("last_name"));
				user.setAge(res.getInt("age"));
				address.setId(res.getInt("id_address"));
				address.setCountry(res.getString("country"));
				address.setStreet(res.getString("street"));
				address.setZipCode(res.getInt("zip"));
				user.setAddress(address);
				role.setRoleName(res.getString("role_name"));
				role.setId(res.getInt("id_role"));
				user.setRole(role);
				music.setTypeName(res.getString("type_name"));
				music.setId(res.getInt("id_music"));
				setMusic.add(music);
				user.setMusicTypes(setMusic);
				set.add(user);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	@Override
	public User read(int id) throws NotFoundException {

		User user = new User();
		Set<MusicType> set = new HashSet<>();

		Connection con = ConnectionDB.getConnection();
		try {
			PreparedStatement pr = con.prepareStatement(READ_USER_BY_ID);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			if (!res.next()) {
				throw new NotFoundException("No user with this ID");
			}

			user.setId(res.getInt("id"));
			user.setLogin(res.getString("login"));
			user.setPassword(res.getString("password"));
			user.setFirstName(res.getString("first_name"));
			user.setLastName(res.getString("last_name"));
			user.setAge(res.getInt("age"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		AddressDaoImpl add = new AddressDaoImpl();
		Address address = new Address();
		address = add.read(id);
		user.setAddress(address);
		try {
			PreparedStatement prRol = con.prepareStatement(GET_USER_ROLE);
			prRol.setInt(1, id);
			ResultSet resultR = prRol.executeQuery();
			Role role = new Role();
			resultR.next();
			role.setId(resultR.getInt("id_role"));
			role.setRoleName(resultR.getString("role_name"));
			user.setRole(role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prst = con.prepareStatement(GET_MUSIC_BY_USER_ID);
			prst.setInt(1, id);
			ResultSet result = prst.executeQuery();
			while (result.next()) {
				MusicType music = new MusicType();
				music.setId(result.getInt("id_music"));
				music.setTypeName(result.getString("type_name"));
				set.add(music);
			}

			user.setMusicTypes(set);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean update(User user) {

		Connection con = ConnectionDB.getConnection();
		boolean updated = false;
		{
			try {
				PreparedStatement ps = con.prepareStatement(UPDATE_USER);
				ps.setString(1, user.getLogin());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getFirstName());
				ps.setString(4, user.getLastName());
				ps.setInt(5, user.getAge());
				RoleDaoImpl role = new RoleDaoImpl();
				Set<Role> setRole = new HashSet<>();
				setRole.addAll(role.read());
				int roleId = 0;
				for (Role roleSet : setRole) {
					if (roleSet.getRoleName().equals(user.getRole().getRoleName())) {
						roleId = roleSet.getId();
					}
				}
				ps.setInt(6, roleId);
				ps.setInt(7, user.getId());

				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				PreparedStatement pst = con.prepareStatement(UPDATE_ADDRESS);
				Address address = user.getAddress();
				pst.setString(1, address.getCountry());
				pst.setString(2, address.getStreet());
				pst.setInt(3, address.getZipCode());
				pst.setInt(4, user.getId());
				pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				PreparedStatement prM = con.prepareStatement(DELETE_OLD_MUSIC_ID_USER_ID);
				prM.setInt(1, user.getId());
				prM.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			MusicTypeDaoImpl musicImpl = new MusicTypeDaoImpl();
			Set<MusicType> types = musicImpl.read();
			Set<MusicType> userTypes = user.getMusicTypes();

			try {
				PreparedStatement prMus = con.prepareStatement(INSERT_NEW_MUSIC_ID_USER_ID);
				for (MusicType t : types) {
					for (MusicType mus : userTypes) {
						if (t.getTypeName().equals(mus.getTypeName())) {
							prMus.setInt(1, user.getId());
							prMus.setInt(2, t.getId());
							prMus.executeUpdate();
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			updated = true;
		}
		return updated;

	}

	@Override
	public boolean delete(int id) {
		 boolean deleted = false;
	        Connection con = ConnectionDB.getConnection();
	        try {
	            PreparedStatement ps = con.prepareStatement(DELETE_USER_MUSIC_SET);
	            ps.setInt(1, id);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        try {
	            PreparedStatement ps3 = con.prepareStatement(DELETE_USER);
	            ps3.setInt(1, id);
	            int j = ps3.executeUpdate();
	            deleted = j == 1;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        AddressDaoImpl addressDao = new AddressDaoImpl();
	        addressDao.delete(id);

	        return deleted;
	}
	
		/*public static void main(String[] str) throws NotFoundException {
		User user = new User();
		//user.setId(1);
		user.setLogin("2");
		user.setPassword("2");
		user.setFirstName("2");
		user.setLastName("www");
		user.setAge(20);
		Role userRole = new Role();
		userRole.setRoleName("USER");
		user.setRole(userRole);
		Address address = new Address();
		address.setCountry("U");
		address.setStreet("ss");
		address.setZipCode(6);
		user.setAddress(address);
		MusicType type1 = new MusicType();
		type1.setTypeName("Folk");
		MusicType type2 = new MusicType();
		type2.setTypeName("Trance");
		//MusicType type3 = new MusicType();
		//type3.setTypeName("Country");
		Set<MusicType> musicTypes = new HashSet<>();
		musicTypes.add(type1);
		musicTypes.add(type2);
		//musicTypes.add(type3);
		user.setMusicTypes(musicTypes);
		UserDaoImpl userImpl = new UserDaoImpl();
		// System.out.println(userImpl.create(user));
		// System.out.println(userImpl.read());
		// System.out.println(userImpl.read(2));
		//System.out.println(userImpl.update(user));
		System.out.println(userImpl.delete(5));
	
		}*/

}
