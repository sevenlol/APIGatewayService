package com.echarm.apigateway.util;


public enum ValidateType {

    NA(0), NON_NULL(1), NON_EMPTY(2), NON_NULL_AND_EMPTY(0);

    public final int id;

    private ValidateType(int id) {
        this.id = id;
    }

    public static ValidateType getValidateType(int id) {
        for (ValidateType type : ValidateType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}
