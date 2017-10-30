package com.qurishimi.touch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qurishimi.touch.encje.Car;
import com.qurishimi.touch.encje.User;

@Component
@Transactional
public class DaoUser {

	@Autowired
	DataSource dataSource;

	@Autowired
	SessionFactory session;

	public List<User> selectUser() {

		String sql = "INSERT INTO `entity`.`user` (`id`, `name`) VALUES ('4', 'Maciek');";

		Connection conn = null;

		try {
			List<User> userlist = new ArrayList<User>();
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			// ps.setString(1, "Jacek");
			User user = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				userlist.add(user);
				System.out.println(rs);
			}

			rs.close();
			ps.close();
			return userlist;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public List<User> moreThan(int value) throws SQLException {

		String statement = "SELECT * FROM user WHERE id <= ?";
		List<User> userlist = new ArrayList<User>();
		Connection conn = dataSource.getConnection();

		PreparedStatement ps = conn.prepareStatement(statement);
		ps.setLong(1, value);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User user = new User();

			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			userlist.add(user);

		}

		return userlist;

	}

	public List<User> list() {

		return session.getCurrentSession().createQuery("from User").list();
	}

	public List<Car> listCar() {

		return session.getCurrentSession().createQuery("from Car").list();
	}
	
	public boolean saveOrUpdate(User user) {

		session.getCurrentSession().saveOrUpdate(user);
		return true;
	}

	public boolean updateValue() {

		List<User> list = session.getCurrentSession().createQuery("from User where id = 2").list();
         
	//	for (User r : list) {
			
	//		for(Car l: r.getCars()) {
	//		System.out.println(l.getModel() + " " + l.getId() + " " + l.getUser().getName());
	//		}
	//	}

		
		
		
		return true;
		
		
	}
	
	
}
