package com.echarm.apigateway.accountsystem.util;

import com.echarm.apigateway.accountsystem.model.DoctorInfo;

public class DoctorInfoFieldChecker {
	/*
	 * true => pass
	 * false => not pass
	 */

	public static enum CheckField {
		name(0),
		gender(1),
		phoneNumber(2),
		address(3),

		category(4),
		currentHospital(5),
		college(6),
		title(7),
		specialty(8),
		availableTime(9),
		facebookAccount(10),
		blogUrl(11);

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

	public DoctorInfoFieldChecker(ConnectType connectType) {
		if (connectType != null)
			this.connectType = connectType;

		for (int i = 0; i < checkArray.length; i++) {
			checkArray[i] = CheckType.NA;
		}
	}


	/* check array setter function */

	public DoctorInfoFieldChecker setChecker(CheckField field, CheckType type) {
		this.checkArray[field.getValue()] = type;
		return this;
	}

	public boolean check(DoctorInfo info) {
		if (info == null)
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
				result = result && checkDoctorInfoField(c, checkArray[c.getValue()], info);
			} else if (connectType == ConnectType.NOT_ALL_FAIL) {
				if (checkDoctorInfoField(c, checkArray[c.getValue()], info))
					return true;
			}
		}

		return result;
	}

	private boolean checkDoctorInfoField(CheckField field, CheckType type, DoctorInfo info) {
		switch (field) {
			case name:
				return checkFieldType(type, info.getName());
			case gender:
				return checkFieldType(type, info.getGender());
			case phoneNumber:
				return checkFieldType(type, info.getPhoneNumber());
			case address:
				return checkFieldType(type, info.getAddress());
			case category:
				return checkFieldType(type, info.getCategory());
			case currentHospital:
				return checkFieldType(type, info.getCurrentHospital());
			case college:
				return checkFieldType(type, info.getCollege());
			case title:
				return checkFieldType(type, info.getTitle());
			case specialty:
				return checkFieldType(type, info.getSpecialty());
			case availableTime:
				return checkFieldType(type, info.getAvailableTime());
			case facebookAccount:
				return checkFieldType(type, info.getFacebookAccount());
			case blogUrl:
				return checkFieldType(type, info.getBlogUrl());
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
