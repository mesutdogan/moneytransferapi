package com.revolut.moneytransferapi.service;

public interface BaseService<K extends Number, V> {
	V findById(K id);

	V persist(V entity);

	V update(V entity);
}
