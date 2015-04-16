package com.echarm.apigateway.accountsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.echarm.apigateway.accountsystem.util.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DoctorInfo extends UserInfo{
    private Category category;
    private String currentHospital;
    private String college;
    private String title;
    private String specialty;
    private String availableTime;
    private String facebookAccount;
    private String blogUrl;


    public DoctorInfo() {
        super();
    }

    /*
     * setter
     */

    @JsonIgnore
    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonProperty("category")
    public void setCategoryFromString(String category) {
        if (category == null || Category.isCategoryExist(category) == null)
            this.category = null;
        else
            this.category = Category.valueOf(category);
    }

    @JsonProperty("current_hospital")
    public void setCurrentHospital(String currentHospital) {
        this.currentHospital = currentHospital;
    }

    @JsonProperty("college")
    public void setCollege(String college) {
        this.college = college;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("specialty")
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @JsonProperty("available_time")
    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    @JsonProperty("facebook_account")
    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    @JsonProperty("blog_url")
    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    /*
     * getter
     */

    @JsonIgnore
    public Category getCategory() {
        return category;
    }

    @JsonProperty("category")
    public String getCategoryString() {
        if (category == null)
            return null;
        else
            return category.name();
    }

    @JsonProperty("current_hospital")
    public String getCurrentHospital() {
        return currentHospital;
    }

    @JsonProperty("college")
    public String getCollege() {
        return college;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("specialty")
    public String getSpecialty() {
        return specialty;
    }

    @JsonProperty("available_time")
    public String getAvailableTime() {
        return availableTime;
    }

    @JsonProperty("facebook_account")
    public String getFacebookAccount() {
        return facebookAccount;
    }

    @JsonProperty("blog_url")
    public String getBlogUrl() {
        return blogUrl;
    }

    @JsonIgnore
    @Override
    public boolean isAllRequiredFieldNonNull() {
        return super.isAllRequiredFieldNonNull() &&
               category != null && currentHospital != null && college != null &&
               title != null && specialty != null && availableTime != null &&
               facebookAccount != null && blogUrl != null;
    }

    @JsonIgnore
    @Override
    public boolean isAllRequiredNonNullFieldNonEmpty() {
        return super.isAllRequiredNonNullFieldNonEmpty() &&
               (currentHospital == null || !currentHospital.equals("")) &&
               (college == null || !college.equals("")) &&
               (title == null || !title.equals("")) &&
               (specialty == null || !specialty.equals("")) &&
               (availableTime == null || !availableTime.equals("")) &&
               (facebookAccount == null || !facebookAccount.equals("")) &&
               (blogUrl == null || !blogUrl.equals(""));
    }

    @JsonIgnore
    @Override
    public String[] getNullRequiredInputFieldName() {
    	String[] strArr = super.getNullRequiredInputFieldName();
    	List<String> nullList = new ArrayList<String>();
    	if(strArr != null)
    		nullList = new ArrayList<String>(Arrays.asList(strArr));

    	if(category == null)
    		nullList.add("DoctorInfo:category");
    	if(currentHospital == null)
    		nullList.add("DoctorInfo:currentHospital");
    	if(college == null)
    		nullList.add("DoctorInfo:college");
    	if(title == null)
    		nullList.add("DoctorInfo:title");
    	if(specialty == null)
    		nullList.add("DoctorInfo:specialty");
    	if(availableTime == null)
    		nullList.add("DoctorInfo:availableTime");
    	if(facebookAccount == null)
            nullList.add("DoctorInfo:facebookAccount");
    	if(blogUrl == null)
            nullList.add("DoctorInfo:blogUrl");

    	if(nullList.isEmpty())
    		return null;
    	else
    		return nullList.toArray(new String[nullList.size()]);
    }

    @JsonIgnore
    @Override
    public String[] getEmptyRequiredInputFieldName() {
        // allow empty for facebookAccount and blogUrl for the moment
        String[] strArr = super.getEmptyRequiredInputFieldName();

        List<String> emptyList = new ArrayList<String>();

        if(strArr != null)
            emptyList = new ArrayList<String>(Arrays.asList(strArr));

        if(currentHospital != null && currentHospital.equals(""))
            emptyList.add("DoctorInfo:currentHospital");
        if(college != null && college.equals(""))
            emptyList.add("DoctorInfo:college");
        if(title != null && title.equals(""))
            emptyList.add("DoctorInfo:title");
        if(specialty != null && specialty.equals(""))
            emptyList.add("DoctorInfo:specialty");
        if(availableTime != null && availableTime.equals(""))
            emptyList.add("DoctorInfo:availableTime");
//        if(facebookAccount != null && facebookAccount.equals(""))
//            emptyList.add("DoctorInfo:facebookAccount");
//        if(blogUrl != null && blogUrl.equals(""))
//            emptyList.add("DoctorInfo:blogUrl");

        if(emptyList.isEmpty())
            return null;
        else
            return emptyList.toArray(new String[emptyList.size()]);
    }

    @JsonIgnore
    @Override
    public String[] getNonNullCapitalCamelName() {
    	String[] strArr = super.getNonNullCapitalCamelName();
    	List<String> fieldList = new ArrayList<String>();
    	if(strArr != null)
    		fieldList = new ArrayList<String>(Arrays.asList(strArr));

    	if(category != null)
    		fieldList.add("Category");
    	if(currentHospital != null)
    		fieldList.add("CurrentHospital");
    	if(college != null)
    		fieldList.add("College");
    	if(title != null)
    		fieldList.add("Title");
    	if(specialty != null)
    		fieldList.add("Specialty");
    	if(availableTime != null)
    		fieldList.add("AvailableTime");

    	if(fieldList.isEmpty())
    		return null;
    	else
    		return fieldList.toArray(new String[fieldList.size()]);
    }
}
