package com.revolut.moneytransferapi.entity;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MoneyTransferDto {
	@NotNull
	private Long senderId;
	@NotNull
	private Long receiverId;
	@NotNull
	private String bankCode;
	@NotNull
	private String explanation;
	@NotNull
	private BigDecimal amount;

	public MoneyTransferDto() {
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
