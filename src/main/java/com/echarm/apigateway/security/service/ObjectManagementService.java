package com.echarm.apigateway.security.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.echarm.apigateway.security.model.ObjectSummary;

public interface ObjectManagementService {

    public List<ObjectSummary> upload(MultipartFile[] files);

    public byte[] download(String key);

    public List<ObjectSummary> list();
}
