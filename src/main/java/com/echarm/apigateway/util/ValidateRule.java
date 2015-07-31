package com.echarm.apigateway.util;

public enum ValidateRule {

    ALL_PASS(0), NOT_ALL_FAIL(1);

    public final int id;

    private ValidateRule(int id) {
        this.id = id;
    }

    public static ValidateRule getValidateRule(int id) {
        for (ValidateRule rule : ValidateRule.values()) {
            if (rule.id == id) {
                return rule;
            }
        }
        return null;
    }
}
