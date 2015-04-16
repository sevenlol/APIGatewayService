package com.echarm.apigateway.accountsystem.util;

public enum Category {
	Category_1,
	Category_2,
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
