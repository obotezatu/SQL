package com.foxminded.dao;

import java.util.List;

public interface Dao<T> {

	void insert(T entity) throws DaoException;

	T getById(int id) throws DaoException;

	void update(T entity) throws DaoException;

	void delete(T entity) throws DaoException;

	List<T> getAll() throws DaoException;

}
