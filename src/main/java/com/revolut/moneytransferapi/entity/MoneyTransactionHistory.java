package com.revolut.moneytransferapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
public class MoneyTransactionHistory {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@NotNull
	private Long id;
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
	private LocalDateTime requestDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MoneyTransactionHistory that = (MoneyTransactionHistory) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "MoneyTransactionHistory{" +
				"id=" + id +
				", senderId=" + senderId +
				", receiverId=" + receiverId +
				", bankCode='" + bankCode + '\'' +
				", explanation='" + explanation + '\'' +
				", amount=" + amount +
				", requestDate=" + requestDate +
				'}';
	}
}
