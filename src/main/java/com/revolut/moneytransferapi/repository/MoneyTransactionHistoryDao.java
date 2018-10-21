package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.entity.MoneyTransactionHistory;
import com.revolut.moneytransferapi.service.BaseService;
import com.revolut.moneytransferapi.util.HiibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MoneyTransactionHistoryDao implements BaseService<Long, MoneyTransactionHistory> {

	private static MoneyTransactionHistoryDao INSTANCE;

	private SessionFactory sessionFactory = HiibernateUtil.getSessionFactory();

	private MoneyTransactionHistoryDao() {

	}

	public static MoneyTransactionHistoryDao getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MoneyTransactionHistoryDao();
		}
		return INSTANCE;
	}

	@Override
	public MoneyTransactionHistory findById(Long id) {
		return null;
	}

	@Override
	public MoneyTransactionHistory persist(MoneyTransactionHistory entity) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		session.save(entity);
		transaction.commit();
		return entity;
	}

	@Override
	public MoneyTransactionHistory update(MoneyTransactionHistory entity) {
		return null;
	}
}
