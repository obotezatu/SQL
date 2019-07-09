package com.foxminded.obotezatu;

import java.util.List;

public interface Dao<T> {

	public void insert(T student);

	public T getRecordById(String id);

	public void update(T student);

	public void delete(T student);

	public List<T> getAll();

}
