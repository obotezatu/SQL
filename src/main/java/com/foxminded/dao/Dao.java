package com.foxminded.dao;

import java.sql.Connection;
import java.util.List;

public interface Dao<T> {

	void insert(T student, Connection connect);

	T getById(int id, Connection connect);

	void update(T student, Connection connect);

	void delete(T student, Connection connect);

	List<T> getAll(Connection connection);

}
