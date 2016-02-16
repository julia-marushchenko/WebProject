package com.daoImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import com.java.Address;
import com.java.NotFoundException;
import com.dao.Dao;


public class AddressDaoImpl implements Dao<Address> {
	
	private final static String CREATE_ADDRESS = "INSERT INTO address (country,street,zip) VALUES (?,?,?)";
    private final static String READ_ALL_ADDRESS = "SELECT * FROM address";
    private final static String SHOW_ADDRESS_WITH_ID = "SELECT * FROM address WHERE id_address=?";
    private final static String UPDATE_ADDRESS = "UPDATE address SET country=?, street=?, zip=? WHERE id_address=?";
    private final static String DELETE_ADDRESS = "DELETE FROM address WHERE id_address=?";

    
    
	@Override
	public Address create(Address obj) {
		Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement pr = con.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, obj.getCountry());
            pr.setString(2, obj.getStreet());
            pr.setInt(3,obj.getZipCode());
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
	public Set<Address> read() {
		Connection con = ConnectionDB.getConnection();
        Set <Address> set = new HashSet<>();

        try {
            PreparedStatement pr = con.prepareStatement(READ_ALL_ADDRESS);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id_address");
                String country = rs.getString("country");
                String street = rs.getString("street");
                int zipCode = rs.getInt("zip");
                Address address = new Address();
                address.setId(id);
                address.setCountry(country);
                address.setStreet(street);
                address.setZipCode(zipCode);
                set.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
	}

	@Override
	public Address read(int id) throws NotFoundException {
		Connection con = ConnectionDB.getConnection();
		Address address = new Address();
		try {
			PreparedStatement pr = con.prepareStatement(SHOW_ADDRESS_WITH_ID);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			if (!res.next()) {
				throw new NotFoundException("No user with this ID");
			}
			int idAdd = res.getInt("id_address");
            String country = res.getString("country");
            String street = res.getString("street");
            int zipCode = res.getInt("zip");
            address.setId(idAdd);
            address.setCountry(country);
            address.setStreet(street);
            address.setZipCode(zipCode);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public boolean update(Address obj) {
		
		Connection con = ConnectionDB.getConnection();
		boolean updated = false;
		try {
			PreparedStatement ps = con.prepareStatement(UPDATE_ADDRESS);
			ps.setString(1, obj.getCountry());
			ps.setString(2, obj.getStreet());
			ps.setInt(3, obj.getZipCode());
			ps.setInt(4, obj.getId());
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
			PreparedStatement ps = con.prepareStatement(DELETE_ADDRESS);
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
