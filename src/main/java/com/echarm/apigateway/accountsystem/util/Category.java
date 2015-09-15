package com.echarm.apigateway.accountsystem.util;

public enum Category {
    PGY, plasticsurgeon, cosmeticsurgeon, dermatologist, OBSGYN, pediatrician,
    occupational_therapist, physical_therapist, pharmacist, nutritionist, oncologist, others,
	Arbitrary;

	public static Category isCategoryExist(String category) {
		for (Category c : Category.values()) {
			if (c.name().equalsIgnoreCase(category)) {
				return c;
			}
		}
		return null;
	}

}
