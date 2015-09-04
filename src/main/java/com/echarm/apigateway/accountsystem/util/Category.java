package com.echarm.apigateway.accountsystem.util;

public enum Category {
    PGY, plasticsurgeon, cosmeticsurgeon, dermatologist, OBSGYN, pediatrics, others,
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
