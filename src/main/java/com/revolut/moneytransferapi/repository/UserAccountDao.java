package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.entity.UserAccount;
import com.revolut.moneytransferapi.exxception.NotEnoughBalanceException;
import com.revolut.moneytransferapi.exxception.UserAccountNotFoundException;
import com.revolut.moneytransferapi.service.UserAccountService;
import com.revolut.moneytransferapi.util.HiibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Account related operations are done in this class.
 *
 * @author mesut
 */
public class UserAccountDao implements BaseDao<Long, UserAccount> {

	private static final Logger LOGGER = Logger.getLogger(UserAccountService.class.getName());

	private static UserAccountDao INSTANCE;

	private SessionFactory sessionFactory = HiibernateUtil.getSessionFactory();

	private Lock lock = new ReentrantLock();

	private UserAccountDao() {

	}

	public static UserAccountDao getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserAccountDao();
		}
		return INSTANCE;
	}

	/**
	 * Thread holds the lock on {@link #lock} object and prevent another threads
	 * to call {@link #deposit(BigDecimal, Long)} or {@link #withdraw(BigDecimal, Long)} methods.
	 *
	 * @param transferAmount
	 * @param fromAccountId
	 * @param toAccountId
	 * @return
	 */
	public boolean moneyTransfer(BigDecimal transferAmount, Long fromAccountId, Long toAccountId) {

		acquireTheLock();
		UserAccount fromAccount;
		UserAccount toAccount;

		try {
			fromAccount = findById(fromAccountId);
			toAccount = findById(toAccountId);
			checkIfUserExists(fromAccountId, fromAccount);
			checkIfUserExists(toAccountId, toAccount);
			checkIfBalanceEnough(transferAmount, toAccountId, fromAccount);
			fromAccount.setTotalAmount(fromAccount.getTotalAmount().subtract(transferAmount));
			toAccount.setTotalAmount(toAccount.getTotalAmount().add(transferAmount));
			update(fromAccount);
			update(toAccount);
			LOGGER.info("Money transfer has been completed.");
			return true;
		} catch (HibernateException e) {
			LOGGER.severe("Exception occurred on db.");
			e.printStackTrace();
			return false;
		} finally {
			releaseTheLock();
		}
	}

	private void checkIfBalanceEnough(BigDecimal transferAmount, Long toAccountId, UserAccount fromAccount)
			throws NotEnoughBalanceException {
		if (fromAccount.getTotalAmount().subtract(transferAmount).longValue() < 0) {
			throw new NotEnoughBalanceException("UserAccount Not Found With Id : " + toAccountId);
		}
	}

	private void checkIfUserExists(Long fromAccountId, UserAccount fromAccount) throws UserAccountNotFoundException {
		if (fromAccount == null) {
			throw new UserAccountNotFoundException("UserAccount Not Found With Id : " + fromAccountId);
		}
	}

	/**
	 * Deposits with a given amount.
	 * Thread holds the lock on {@link #lock} object and prevent another threads
	 * to call {@link #moneyTransfer(BigDecimal, Long, Long)} or {@link #withdraw(BigDecimal, Long)} methods.
	 *
	 * @param depositAmount
	 * @param accountId
	 * @return
	 */
	public UserAccount deposit(BigDecimal depositAmount, Long accountId) {
		acquireTheLock();
		Session session = sessionFactory.openSession();
		UserAccount userAccount = null;
		Transaction transaction = null;
		try {
			userAccount = session.get(UserAccount.class, accountId);
			userAccount.setTotalAmount(userAccount.getTotalAmount().add(depositAmount));
			transaction = session.beginTransaction();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
			releaseTheLock();
		}
		return userAccount;
	}

	private void acquireTheLock() {
		lock.lock();
	}

	/**
	 * Withdraws the money with a given amount.
	 * Thread holds the lock on {@link #lock} object and prevent another threads
	 * to call {@link #deposit(BigDecimal, Long)} or {@link #moneyTransfer(BigDecimal, Long, Long)}  methods.
	 *
	 * @param depositAmount
	 * @param accountId
	 * @return
	 */
	public UserAccount withdraw(BigDecimal depositAmount, Long accountId) {
		acquireTheLock();
		Session session = sessionFactory.openSession();
		UserAccount userAccount = null;
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			userAccount = findById(accountId);
			userAccount.setTotalAmount(userAccount.getTotalAmount().subtract(depositAmount));
			session.update(userAccount);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
			releaseTheLock();
		}
		return userAccount;
	}

	private void releaseTheLock() {
		lock.unlock();
	}

	@Override
	public UserAccount findById(Long id) {
		Session session = null;
		UserAccount userAccount;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			userAccount = session.get(UserAccount.class, id);
			transaction.commit();
		} finally {
			session.close();

		}
		return userAccount;
	}

	@Override
	public UserAccount persist(UserAccount entity) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			session.persist(entity);
			transaction.commit();
		} finally {
			session.close();
		}

		return entity;
	}

	@Override
	public UserAccount update(UserAccount entity) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		} finally {
			session.close();
		}

		return entity;
	}
}
