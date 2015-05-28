package com.echarm.apigateway.accountsystem.util;

import com.echarm.apigateway.accountsystem.model.Account;

public class AccountFieldChecker {

	/*
	 * true => pass
	 * false => not pass
	 */

	public static enum CheckField {
		accountId(0),
		userType(1),
		accountType(2),
		email(3),
		username(4),
		salt(5),
		password(6),
		createdAt(7),
		userInfo(8);

		private final int value;
		private CheckField(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum CheckType {
		NA,
		NON_NULL,
		NON_EMPTY,
		BOTH
	}

	public static enum ConnectType {
		ALL_PASS,
		NOT_ALL_FAIL
	}

	private CheckType[] checkArray = new CheckType[CheckField.values().length];
	private ConnectType connectType = ConnectType.ALL_PASS;


	public AccountFieldChecker(ConnectType connectType) {
		if (connectType != null)
			this.connectType = connectType;

		for (int i = 0; i < checkArray.length; i++) {
			checkArray[i] = CheckType.NA;
		}
	}

	/* check array setter function */

	public AccountFieldChecker setChecker(CheckField field, CheckType type) {
		this.checkArray[field.getValue()] = type;
		return this;
	}

	public boolean check(Account account) {
		if (account == null)
			return false;

		boolean result;

		switch (connectType) {
			case ALL_PASS:
				result = true;
				break;
			case NOT_ALL_FAIL:
				result = false;
				break;
			default:
				return false;
		}

		for (CheckField c : CheckField.values()) {
			if (checkArray[c.getValue()] == CheckType.NA)
				continue;

			if (connectType == ConnectType.ALL_PASS) {
				result = result && checkAccountField(c, checkArray[c.getValue()], account);
			} else if (connectType == ConnectType.NOT_ALL_FAIL) {
				if (checkAccountField(c, checkArray[c.getValue()], account))
					return true;
			}
		}

		return result;
	}

	private boolean checkAccountField(CheckField field, CheckType type, Account account) {
		switch (field) {
			case accountId:
				return checkFieldType(type, account.getAccountId());
			case userType:
				return checkFieldType(type, account.getUserType());
			case accountType:
				return checkFieldType(type, account.getAccountType());
			case username:
				return checkFieldType(type, account.getUserName());
			case salt:
				return checkFieldType(type, account.getSalt());
			case password:
				return checkFieldType(type, account.getPassword());
			case createdAt:
				return checkFieldType(type, account.getCreatedAt());
			case email:
				return checkFieldType(type, account.getEmail());
			case userInfo:
				return checkFieldType(type, account.getUserInfo());
			default:
		}

		return false;
	}

	private boolean checkFieldType(CheckType type, Object field) {
		switch (type) {
			case NON_NULL:
				return field != null;
			case NON_EMPTY:
				if (!(field instanceof String))
					return true;

				return field == null || !((String) field).equals("");
			case BOTH:
				if (field == null)
					return false;

				if (!(field instanceof String))
					return true;

				return !((String) field).equals("");
			default:
		}
		return true;
	}
}
