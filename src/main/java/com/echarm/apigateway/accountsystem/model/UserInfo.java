package com.echarm.apigateway.accountsystem.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class UserInfo {
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String address;

    public enum Gender{
        MALE,
        FEMALE;

        public static Gender isGenderExist(String gender) {
            for (Gender g : Gender.values()) {
                if (g.name().equalsIgnoreCase(gender))
                    return g;
            }
            return null;
        }
    }

    public UserInfo() {

    }

    /*
     * setter
     */

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    protected void setGender(Gender gender) {
        this.gender = gender;
    }

    @JsonProperty("gender")
    public void setGenderWithString(String gender) {
        if (gender == null || Gender.isGenderExist(gender) == null)
            this.gender = null;
        else
            this.gender = Gender.valueOf(gender);
    }

    @JsonProperty("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    /*
     * getter
     */

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonIgnore
    public Gender getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public String getGenderString() {
        if (gender == null)
            return null;
        else
            return gender.name();
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonIgnore
    public boolean isAllRequiredFieldNonNull() {
        // only DoctorAccounts need to check info
        if (!(this instanceof DoctorInfo))
            return true;
        return name != null && gender != null &&
               phoneNumber != null && address != null;
    }

    @JsonIgnore
    public boolean isAllRequiredNonNullFieldNonEmpty() {
        // only DoctorAccounts need to check info
        if (!(this instanceof DoctorInfo))
            return true;
        return (name == null || !name.equals("")) &&
               (phoneNumber == null || !phoneNumber.equals("")) &&
               (address == null || !address.equals(""));
    }

    @JsonIgnore
    public String[] getNullRequiredInputFieldName() {
        if (!(this instanceof DoctorInfo))
            return null;

    	List<String> nullList = new ArrayList<String>();

    	if(name == null)
    		nullList.add("UserInfo:name");
    	if(gender == null)
    		nullList.add("UserInfo:gender");
    	if (phoneNumber == null)
    	    nullList.add("UserInfo:phoneNumber");
    	if (address == null)
    	    nullList.add("UserInfo:address");

    	if(nullList.isEmpty())
    		return null;
    	else
    		return nullList.toArray(new String[nullList.size()]);
    }

    @JsonIgnore
    public String[] getEmptyRequiredInputFieldName() {
        if (!(this instanceof DoctorInfo))
            return null;

        List<String> emptyList = new ArrayList<String>();

        if(name != null && name.equals(""))
            emptyList.add("UserInfo:name");
        if (phoneNumber != null && phoneNumber.equals(""))
            emptyList.add("UserInfo:phoneNumber");
        if (address != null && address.equals(""))
            emptyList.add("UserInfo:address");

        if(emptyList.isEmpty())
            return null;
        else
            return emptyList.toArray(new String[emptyList.size()]);
    }

    @JsonIgnore
    public String[] getNonNullCapitalCamelName() {
    	List<String> fieldNameList = new ArrayList<String>();

    	if (name != null)
    		fieldNameList.add("Name");

    	if (gender != null)
    		fieldNameList.add("GenderString");

    	if (phoneNumber != null)
    		fieldNameList.add("PhoneNumber");

    	if (address != null)
    		fieldNameList.add("Address");

    	if (fieldNameList.size() > 0)
            return fieldNameList.toArray(new String[fieldNameList.size()]);

    	return null;
    }
}
