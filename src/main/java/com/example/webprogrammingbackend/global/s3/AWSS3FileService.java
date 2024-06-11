package com.example.webprogrammingbackend.global.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.webprogrammingbackend.global.exception.DomainException;
import com.example.webprogrammingbackend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AWSS3FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void putObject(MultipartFile file, String key , String filename) throws DomainException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        try {
            amazonS3Client.putObject(bucket, key+ "/" + filename, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new DomainException(ErrorCode.INVALID_IMAGE_FILE);
        }
    }

    public static String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos );
    }

    // TODO getImageUrl 구현
    public static String getImageUrl(String key, String filename){
        return "couponwallet.s3.ap-northeast-2.amazonaws.com/"+ key + "/" +filename;
    }
}