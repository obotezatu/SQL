package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao implements Dao<Group> {

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/foxminded", "postgres", "1");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	@Override
	public void insert(Group group) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("insert into groups(group_id, name) values(?,?)")) {
			ps.setString(1, group.getGroupId());
			ps.setString(2, group.getName());
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public Group getRecordById(String groupId) {
		Group group = new Group();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("select * from groups where group_id=?")) {
			ps.setString(1, groupId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					group.setGroupId(groupId);
					group.setName(rs.getString("name"));
				}
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public void update(Group group) {	
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("update groups set group_id=?, name=?")){
			ps.setString(1, group.getGroupId());
			ps.setString(2, group.getName());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Group group) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("delete from groups where group_id=?")) {
			ps.setString(1, group.getGroupId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public List<Group> getAll() {
		List<Group> groups = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM groups");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Group group = new Group();
				group.setGroupId(rs.getString("group_id"));
				group.setName(rs.getString("name"));
				groups.add(group);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return groups;
	}
}
