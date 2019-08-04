package com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.domain.Group;

public class GroupDao implements Dao<Group> {

	private static final String INSERT = "INSERT INTO groups(group_name) VALUES(?)";
	private static final String GET_BY_ID = "SELECT * FROM groups WHERE group_id=?";
	private static final String UPDATE = "UPDATE groups SET group_id=?, group_name=?";
	private static final String DELETE = "DELETE FROM groups WHERE group_id=?";
	private static final String GET_ALL = "SELECT * FROM groups";
	
	private DataSource dataSource;

	public GroupDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Group group){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT)) {
			statement.setString(1, group.getGroupName());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Group getById(int groupId) throws DaoException {
		Group group = new Group();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setInt(1, groupId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next() != false) {
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
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setInt(1, group.getGroupId());
			statement.setString(2, group.getGroupName());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Cannot update group.", e);
		}
	}

	@Override
	public void delete(Group group) throws DaoException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, group.getGroupId());
		} catch (Exception e) {
			throw new DaoException("Cannot delete group.", e);
		}
	}

	@Override
	public List<Group> getAll() throws DaoException {
		List<Group> groups = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_ALL);
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
