package com.foxminded.obotezatu;

import java.sql.Connection;
import java.util.List;

public interface Dao<T> {

	public void insert(T student, Connection connect);

	public T getRecordById(int id, Connection connect);

	public void update(T student, Connection connect);

	public void delete(T student, Connection connect);

	public List<T> getAll(Connection connection);

}
