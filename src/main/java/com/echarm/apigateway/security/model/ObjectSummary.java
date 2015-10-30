package com.echarm.apigateway.security.model;

public class ObjectSummary {

    private String fileName;
    private String url;

    public ObjectSummary() {}

    /* getter methods */

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    /* setter methods */

    public ObjectSummary setFileName(String fileName) {
        this.fileName = fileName; return this;
    }

    public ObjectSummary setUrl(String url) {
        this.url = url; return this;
    }
}
