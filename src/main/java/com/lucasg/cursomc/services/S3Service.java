package com.lucasg.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    @Value("${s3.bucket}")
    private String bucketName;

    private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);

    private final AmazonS3 s3client;

    @Autowired
    public S3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public void uploadFile(String localFilePath) {
        try {

            File file = new File(localFilePath);

            LOG.info("Iniciando upload");
            s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
            LOG.info("Upload Finalizado");
        } catch (AmazonServiceException e) {
            LOG.info("AmazonServiceException: {}", e.getMessage());
            LOG.info("Status Code: {}", e.getErrorCode());
        } catch (AmazonClientException e) {
            LOG.info("AmazonClientException: {}", e.getMessage());
        }
    }

}
