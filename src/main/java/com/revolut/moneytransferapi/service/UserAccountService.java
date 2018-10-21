package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.entity.MoneyTransactionHistory;
import com.revolut.moneytransferapi.entity.MoneyTransferDto;
import com.revolut.moneytransferapi.entity.UserAccount;
import com.revolut.moneytransferapi.repository.UserAccountDao;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class UserAccountService implements BaseService<Long, UserAccount> {

	private static final Logger LOGGER = Logger.getLogger(UserAccountService.class.getName());

	private UserAccountDao userAccountDao = UserAccountDao.getInstance();

	private MoneyTransactionHistoryService moneyTransactionHistoryService = MoneyTransactionHistoryService
			.getInstance();

	private static UserAccountService INSTANCE;

	private UserAccountService() {

	}

	public static UserAccountService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserAccountService();
		}
		return INSTANCE;
	}

	public boolean moneyTransfer(MoneyTransferDto moneyTransferDto) {
		boolean result = userAccountDao.moneyTransfer(moneyTransferDto.getAmount(), moneyTransferDto.getSenderId(),
				moneyTransferDto.getReceiverId());
		if (result) {
			try {
				MoneyTransactionHistory moneyTransactionHistory = new MoneyTransactionHistory();
				moneyTransactionHistory.setAmount(moneyTransferDto.getAmount());
				moneyTransactionHistory.setBankCode(moneyTransferDto.getBankCode());
				moneyTransactionHistory.setExplanation(moneyTransferDto.getExplanation());
				moneyTransactionHistory.setReceiverId(moneyTransferDto.getReceiverId());
				moneyTransactionHistory.setRequestDate(LocalDateTime.now());
				moneyTransactionHistory.setSenderId(moneyTransferDto.getSenderId());
				moneyTransactionHistoryService.persist(moneyTransactionHistory);
			} catch (RuntimeException e) {
				LOGGER.warning("Money transfer has been completed but could not be added to history.");
			}
		}
		return result;
	}

	@Override
	public UserAccount findById(Long id) {
		return userAccountDao.findById(id);
	}

	@Override
	public UserAccount persist(UserAccount entity) {
		return userAccountDao.persist(entity);
	}

	@Override
	public UserAccount update(UserAccount entity) {
		return userAccountDao.update(entity);
	}
}
