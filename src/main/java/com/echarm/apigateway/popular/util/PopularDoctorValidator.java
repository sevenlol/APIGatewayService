package com.echarm.apigateway.popular.util;

import java.util.Map;
import java.util.Set;

import com.echarm.apigateway.popular.model.PopularDoctor;
import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularDoctorValidator {
    private ValidateRule rule;
    private Map<PopularDoctorField, ValidateType> map;
    private StringBuilder builder;

    public PopularDoctorValidator(ValidateRule rule, Map<PopularDoctorField, ValidateType> map) {
        this.rule = rule;
        this.map = map;
        builder = new StringBuilder();
    }

    public boolean validate(PopularDoctor doctor) {
        if (doctor == null) return false;
        if (rule == null || map == null) return false;
        if (map.size() == 0) return true;

        // clear string builder
        builder.setLength(0);
        builder.append("Rule: " + rule);

        Set<PopularDoctorField> fieldSet = map.keySet();
        for (PopularDoctorField field : fieldSet) {
            boolean isFieldValidated = validateField(doctor, field, map.get(field));

            if (rule == ValidateRule.ALL_PASS && !isFieldValidated) {
                builder.append(" => Failed!\nField: " + field + ", Type: " + map.get(field) + "\n");
                return false;
            } else if (rule == ValidateRule.NOT_ALL_FAIL && isFieldValidated) {
                return true;
            }
        }

        if (rule == ValidateRule.ALL_PASS)  return true;
        else if (rule == ValidateRule.NOT_ALL_FAIL) {
            builder.append(" => Failed!\n");
            return false;
        }

        return false;
    }

    public String getValidateStr() {
        if (builder != null)
            return builder.toString();
        else
            return null;
    }

    private boolean validateField(PopularDoctor doctor, PopularDoctorField field, ValidateType type) {
        if (doctor == null || field == null || type == null) return false;

        switch(field) {
            case DOCTOR_ID:
                return validate(doctor.getDoctorId(), type);
            case DOCTOR_CATEGORY:
                return validate(doctor.getDoctorCategory(), type);
            case DOCTOR_NAME:
                return validate(doctor.getDoctorName(), type);
            case DOCTOR_STICKER_URL:
                return validate(doctor.getDoctorStickerUrl(), type);
            default:
        }

        return false;
    }

    private boolean validate(Object obj, ValidateType type) {
        if (type == null) return false;

        switch(type) {
            case NA:
                return true;
            case NON_EMPTY:
                // allow obj to be null
                if (obj instanceof String){
                    return !"".equals(obj);
                } else {
                    // this type only works on string
                    return true;
                }
            case NON_NULL:
                return obj != null;
            case NON_NULL_AND_EMPTY:
                if (obj instanceof String) {
                    return obj != null && !"".equals(obj);
                } else {
                    return obj != null;
                }
            default:
        }

        return false;
    }
}
