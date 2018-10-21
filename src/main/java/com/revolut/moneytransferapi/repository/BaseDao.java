package com.revolut.moneytransferapi.repository;

public interface BaseDao<K extends Number, V> {
	V findById(K id);

	V persist(V entity);

	V update(V entity);
}
