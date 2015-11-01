package com.echarm.apigateway.security.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.security.model.ObjectSummary;

@Service
public class AWSS3ObjectManager implements ObjectManagementService {

    @Autowired
    private AmazonS3Client amazonS3Client;

    public AWSS3ObjectManager() {}

    @Override
    public List<ObjectSummary> upload(String bucket, MultipartFile[] files) {

        if (files == null) {
            throw new ServerSideProblemException("multipart file array should not be null!");
        }

        check(bucket);

        if (!doesBucketExists(bucket)) {
            Bucket createdBucket = createBucket(bucket);
            if (createdBucket == null) {
                throw new ServerSideProblemException(
                        String.format("Create bucket %s failed!", bucket == null ? "NULL" : bucket));
            }
        }

        List<ObjectSummary> summaryList = new ArrayList<ObjectSummary>();
        for (MultipartFile file : files) {
            if (file == null) {
                continue;
            }

            PutObjectResult result = null;
            try {
                result = upload(bucket, file.getInputStream(), file.getOriginalFilename());
            } catch (IOException e) {
            }

            if (result != null) {
                summaryList.add(new ObjectSummary()
                    .setFileName(file.getOriginalFilename())
                    .setUrl(getUrl(bucket, file.getOriginalFilename())));
            }
        }

        return summaryList;
    }

    @Override
    public byte[] download(String bucket, String key) {

        if (key == null) {
            throw new ServerSideProblemException("Download key should not be null!");
        }

        check(bucket);

        if (!doesBucketExists(bucket)) {
            throw new ServerSideProblemException(
                    String.format("Bucket %s does not exist!", bucket == null ? "NULL" : bucket));
        }

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        if (objectInputStream == null) {
            return null;
        }

        byte[] bytes = null;
        try {
            bytes = com.amazonaws.util.IOUtils.toByteArray(objectInputStream);
        } catch (IOException e) {
        }
        return bytes;
    }

    @Override
    public List<ObjectSummary> list(String bucket) {

        check(bucket);

        if (!doesBucketExists(bucket)) {
            throw new ServerSideProblemException(
                    String.format("Bucket %s does not exist!", bucket == null ? "NULL" : bucket));
        }

        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));

        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
        List<ObjectSummary> summaryList = new ArrayList<ObjectSummary>();
        if (s3ObjectSummaries == null) {
            return null;
        }
        for (S3ObjectSummary summary : s3ObjectSummaries) {
            if (summary == null) {
                continue;
            }

            summaryList.add(new ObjectSummary()
                    .setFileName(summary.getKey())
                    .setUrl(getUrl(bucket, summary.getKey())));
        }
        return summaryList;
    }

    @Override
    public ObjectSummary delete(String bucket, String key) {

        if (key == null) {
            throw new ServerSideProblemException("Delete key should not be null!");
        }

        check(bucket);

        if (!doesBucketExists(bucket)) {
            throw new ServerSideProblemException(
                    String.format("Bucket %s does not exist!", bucket == null ? "NULL" : bucket));
        }

        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);

        try {
            amazonS3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            return null;
        }
        return new ObjectSummary().setFileName(key).setUrl(getUrl(bucket, key));
    }

    @Override
    public String getKey(ObjectSummary summary) {

        if (summary == null) {
            return null;
        }

        return summary.getFileName() == null ?
               summary.getFileName() :
               getKey(summary.getUrl());
    }

    private PutObjectResult upload(String bucket, InputStream inputStream, String uploadKey) {

        if (inputStream == null || uploadKey == null) {
            return null;
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());

        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

        IOUtils.closeQuietly(inputStream);

        return putObjectResult;
    }

    private void check(String bucket) throws ServerSideProblemException {

        if (bucket == null) {
            throw new ServerSideProblemException("bucket should not be null!");
        }
        if (amazonS3Client == null) {
            throw new ServerSideProblemException("Amazon s3 client should not be null!");
        }
    }

    private String getKey(String url) {

        if (url == null || !url.contains("/")) {
            return null;
        }

        String[] tmp = url.split("/");
        if (tmp != null && tmp.length > 0) {
            return tmp[tmp.length - 1];
        }

        return null;
    }

    private String getUrl(String bucket, String fileName) {

        if (amazonS3Client == null || bucket == null) {
            return null;
        }

        return amazonS3Client.getResourceUrl(bucket, fileName);
    }

    private boolean doesBucketExists(String bucket) {

        if (bucket == null) {
            return false;
        }

        return amazonS3Client.doesBucketExist(bucket);
    }

    private Bucket createBucket(String bucket) {
        if (bucket == null) {
            return null;
        }

        return amazonS3Client.createBucket(bucket);
    }

}
