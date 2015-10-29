package com.echarm.apigateway.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.echarm.apigateway.security.model.ObjectSummary;

@Service
public class AWSS3ObjectManager implements ObjectManagementService {

    @Autowired
    private AmazonS3Client amazonS3Client;
    private String bucket;

    public AWSS3ObjectManager() {}

    public String getBucket() { return bucket; }

    public AWSS3ObjectManager setBucket(String bucket) {
        this.bucket = bucket; return this;
    }

    @Override
    public List<ObjectSummary> upload(MultipartFile[] files) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] download(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ObjectSummary> list() {
        // TODO Auto-generated method stub
        return null;
    }

}
