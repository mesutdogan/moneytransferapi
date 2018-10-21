package com.revolut.moneytransferapi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "user_account")
public class UserAccount {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private BigDecimal totalAmount;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	public Long getId() {
		return id;
	}

	public void setId(Long accountId) {
		this.id = accountId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserAccount that = (UserAccount) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "UserAccount{" +
				"id=" + id +
				", totalAmount=" + totalAmount +
				", accountType=" + accountType +
				'}';
	}
}
