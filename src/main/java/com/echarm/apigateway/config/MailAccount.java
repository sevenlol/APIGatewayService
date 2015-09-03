package com.echarm.apigateway.config;

public class MailAccount {

    private String address;
    private String password;

    public MailAccount() {}

    /* getter methods */

    public String getAddress() { return address; }
    public String getPassword() { return password; }

    /* setter methods */

    public MailAccount setAddress(String address) { this.address = address; return this; }
    public MailAccount setPassword(String password) { this.password = password; return this; }
}
