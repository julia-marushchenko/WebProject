package com.dao;

import java.util.Set;

import com.java.NotFoundException;

public interface Dao <T> {
	T create(T obj);

	Set<T> read();

	T read(int id) throws NotFoundException;

	boolean update(T obj);

	boolean delete(int id);
}
