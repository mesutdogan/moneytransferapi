package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.entity.MoneyTransferDto;
import com.revolut.moneytransferapi.exxception.NotEnoughBalanceException;
import com.revolut.moneytransferapi.exxception.UserAccountNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class UserAccountServiceTest {

	private UserAccountService userAccountService = UserAccountService.getInstance();

	@Test(expected = UserAccountNotFoundException.class)
	public void should_Throw_UserNotAccountFoundException_when_UserAccount_Does_Not_Exists()
			throws NotEnoughBalanceException, UserAccountNotFoundException {
		MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
		moneyTransferDto.setSenderId(0L);
		moneyTransferDto.setReceiverId(1L);
		userAccountService.moneyTransfer(moneyTransferDto);
	}

	@Test(expected = NotEnoughBalanceException.class)
	public void should_Throw_NotEnoughBalanceException_when_UserAccount_Does_Not_Exists()
			throws NotEnoughBalanceException, UserAccountNotFoundException {
		MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
		moneyTransferDto.setSenderId(1L);
		moneyTransferDto.setReceiverId(2L);
		moneyTransferDto.setAmount(BigDecimal.valueOf(50000));
		moneyTransferDto.setBankCode("AZ");
		userAccountService.moneyTransfer(moneyTransferDto);
	}

	@Test
	public void should_Transfer_Money_When_All_Inputs_Are_OK()
			throws NotEnoughBalanceException, UserAccountNotFoundException {
		MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
		moneyTransferDto.setSenderId(1L);
		moneyTransferDto.setReceiverId(2L);
		moneyTransferDto.setAmount(BigDecimal.valueOf(50));
		BigDecimal fromAccountTotalAmountBeforeTransfer = userAccountService.findById(1L).getTotalAmount();
		BigDecimal toAccountTotalAmountBeforeTransfer = userAccountService.findById(2L).getTotalAmount();

		boolean result = userAccountService.moneyTransfer(moneyTransferDto);
		Assert.assertTrue(result);

		BigDecimal fromAccountTotalAmountAfterTransfer = userAccountService.findById(1L).getTotalAmount();
		BigDecimal toAccountTotalAmountAfterTransfer = userAccountService.findById(2L).getTotalAmount();

		Assert.assertTrue(
				fromAccountTotalAmountAfterTransfer.subtract(fromAccountTotalAmountBeforeTransfer).longValue() == -50L);
		Assert.assertTrue(
				toAccountTotalAmountBeforeTransfer.subtract(toAccountTotalAmountAfterTransfer).longValue() == -50L);
	}
}
