package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.entity.MoneyTransactionHistory;
import com.revolut.moneytransferapi.repository.MoneyTransactionHistoryDao;

public class MoneyTransactionHistoryService implements BaseService<Long, MoneyTransactionHistory> {

	private MoneyTransactionHistoryDao moneyTransactionHistoryDao = MoneyTransactionHistoryDao.getInstance();

	private static MoneyTransactionHistoryService INSTANCE;

	private MoneyTransactionHistoryService() {

	}

	public static MoneyTransactionHistoryService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MoneyTransactionHistoryService();
		}
		return INSTANCE;
	}
	@Override
	public MoneyTransactionHistory findById(Long id) {
		//// TODO:Mesut 10/21/2018 Not implemented yet.
		return null;
	}

	@Override
	public MoneyTransactionHistory persist(MoneyTransactionHistory entity) {
		return moneyTransactionHistoryDao.persist(entity);
	}

	@Override
	public MoneyTransactionHistory update(MoneyTransactionHistory entity) {
		//// TODO:Mesut 10/21/2018 Not implemented yet.
		return null;
	}
}
