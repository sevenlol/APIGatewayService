package com.echarm.apigateway.security.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.echarm.apigateway.security.model.ObjectSummary;

public interface ObjectManagementService {

    public List<ObjectSummary> upload(String directory, MultipartFile[] files);

    public byte[] download(String directory, String key);

    public List<ObjectSummary> list(String directory);

    public ObjectSummary delete(String directory, String key);

    public String getKey(ObjectSummary summary);
}
