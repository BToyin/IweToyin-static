package com.toyin.iwetoyin.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.toyin.iwetoyin.BlogPost;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class AwsS3Util {

    private static String bucketName = "elasticbeanstalk-eu-west-1-362975218676";
    private static Regions clientRegion = Regions.EU_WEST_1;
    private static AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

    public static void uploadImageToS3(MultipartFile file)  {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s", "-");
        String filePath = "resources/images/blogPhotos/" + fileName;

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

    //todo: Make the filenames the same for the uploaded word doc and image uploaded
    public static InputStream GetBlogPostImageFromS3(String fileName) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();
            String filePath = "resources/images/blogPhotos/" + fileName;
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, filePath));
            return s3Object.getObjectContent();
        } catch (AmazonS3Exception ex) {
            System.err.println(ex.getErrorMessage());
            System.exit(1);
        }
        return null;
    }

    public static BlogPost GetBlogPostFromS3(String fileName) {
        try {
            String filePath = "resources/blogPages/" + fileName;
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, filePath));

            // Read the content of the Word document
            InputStream inputStream = s3Object.getObjectContent();
            XWPFDocument document = new XWPFDocument(inputStream);

            StringBuilder content1 = new StringBuilder();
            for (int i = 2; i < document.getParagraphs().size(); i++) {
                content1.append(document.getParagraphs().get(i).getText());
            }

            String postId = document.getParagraphs().get(0).getText();
            String title = document.getParagraphs().get(1).getText();
            String content = content1.toString();
            System.out.println("post ID: " + postId);
            System.out.println("Title: " + title);
            System.out.println("Content: " + content);

            // Close the input stream
            inputStream.close();

            BlogPost blogPost = new BlogPost();
            blogPost.setPostId(Integer.parseInt(postId));
            blogPost.setTitle(title);
            blogPost.setPhotoFileName("blog-default-image.jpg");
            blogPost.setContent(content);
            return blogPost;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AmazonS3Exception ex) {
            System.err.println(ex.getErrorMessage());
            System.exit(1);
        }
        return null;
    }
}

