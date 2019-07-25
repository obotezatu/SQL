package com.foxminded.dao;

import java.util.List;

public interface Dao<T> {

	void insert(T dao) throws DaoException;

	T getById(int dao) throws DaoException;

	void update(T dao) throws DaoException;

	void delete(T dao) throws DaoException;

	List<T> getAll() throws DaoException;

}
