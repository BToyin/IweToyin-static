package com.toyin.iwetoyin.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.securitylake.model.S3Exception;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AwsS3Util {

    private static String bucketName = "elasticbeanstalk-eu-west-1-362975218676";
    private static Regions clientRegion = Regions.EU_WEST_1;


    public static void uploadImageToS3(MultipartFile file)  {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s", "-");
        String filePath = "resources/static/images/blogPhotos/" + fileName;

        try {
            byte[] bytes = file.getBytes();
            InputStream stream = new ByteArrayInputStream(bytes);

            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            //Setting the metadata for the request
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            PutObjectRequest request = new PutObjectRequest(bucketName, filePath, stream, metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream GetBlogPostImageFromS3(String fileName) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();
            String filePath = "resources/static/images/blogPhotos/" + fileName;
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, filePath));
            return s3Object.getObjectContent();
        } catch (S3Exception ex) {
            System.err.println(ex.getErrorMessage());
            System.exit(1);
        }
        return null;
    }

}

