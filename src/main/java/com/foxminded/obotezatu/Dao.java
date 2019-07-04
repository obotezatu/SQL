package com.foxminded.obotezatu;

import java.util.List;

public interface Dao<T> {

	public void insert(T adr);

	public T getById(int id);

	public void update(T adr);

	public void delete(T adr);

	public List<T> getAll();
}
