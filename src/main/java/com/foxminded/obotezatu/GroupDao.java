package com.foxminded.obotezatu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao implements Dao<Group> {

	@Override
	public void insert(Group group, Connection connection) {
		try (PreparedStatement preparedStatement = connection.prepareStatement("insert into groups(group_id, group_name) values(?,?)")) {
			preparedStatement.setString(1, group.getGroupId());
			preparedStatement.setString(2, group.getGroupName());
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public Group getRecordById(String groupId, Connection connection) {
		Group group = new Group();
		try (PreparedStatement preparedStatement = connection.prepareStatement("select * from groups where group_id=?")) {
			preparedStatement.setString(1, groupId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					group.setGroupId(groupId);
					group.setGroupName(resultSet.getString("group_name"));
				}
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public void update(Group group, Connection connection) {	
		try(PreparedStatement preparedStatement = connection.prepareStatement("update groups set group_id=?, group_name=?")){
			preparedStatement.setString(1, group.getGroupId());
			preparedStatement.setString(2, group.getGroupName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Group group, Connection connection) {
		try (PreparedStatement preparedStatement = connection.prepareStatement("delete from groups where group_id=?")) {
			preparedStatement.setString(1, group.getGroupId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public List<Group> getAll(Connection connection) {
		List<Group> groups = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM groups");
				ResultSet resultSet= preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Group group = new Group();
				group.setGroupId(resultSet.getString("group_id"));
				group.setGroupName(resultSet.getString("group_name"));
				groups.add(group);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return groups;
	}
}
