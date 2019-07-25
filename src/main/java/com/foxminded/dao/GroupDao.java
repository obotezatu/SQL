package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Group;

public class GroupDao implements Dao<Group> {

	DataSource dataSource = new DataSource();

	@Override
	public void insert(Group group) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("insert into groups(group_name) values(?)")) {
			statement.setString(1, group.getGroupName());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot insert group.", e);
		}
	}

	@Override
	public Group getById(int groupId) throws DaoException {
		Group group = new Group();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("select * from groups where group_id=?")) {
			statement.setInt(1, groupId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					group.setGroupId(groupId);
					group.setGroupName(resultSet.getString("group_name"));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot get group by Id.", e);
		}
		return group;
	}

	@Override
	public void update(Group group) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection
						.prepareStatement("update groups set group_id=?, group_name=?")) {
			statement.setInt(1, group.getGroupId());
			statement.setString(2, group.getGroupName());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot update group.", e);
		}
	}

	@Override
	public void delete(Group group) throws DaoException {
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("delete from groups where group_id=?")) {
			statement.setInt(1, group.getGroupId());
		} catch (Exception e) {
			throw new DaoException("Cannot delete group.", e);
		}
	}

	@Override
	public List<Group> getAll() throws DaoException {
		List<Group> groups = new ArrayList<>();
		try (Connection connection = dataSource.getConnectionFoxy();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM groups");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Group group = new Group();
				group.setGroupId(resultSet.getInt("group_id"));
				group.setGroupName(resultSet.getString("group_name"));
				groups.add(group);
			}
		} catch (Exception e) {
			throw new DaoException("Cannot get all group.", e);
		}
		return groups;
	}
}
