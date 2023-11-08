package com.toyin.iwetoyin.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.toyin.iwetoyin.entity.BlogPost;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // Ensure the filenames the same for the uploaded word doc and image uploaded
    public static InputStream getBlogPostImageFromS3(String fileName) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
        String filePath = "resources/images/blogPhotos/" + fileName;
        String defaultFilePath = "resources/images/blogPhotos/blog-default-image.jpg";

        try {
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, filePath));
            return s3Object.getObjectContent();
        } catch (AmazonS3Exception ex) {
            if (ex.getStatusCode() == 404) {
                // The specified key does not exist, so retry with the default key
                S3Object defaultS3Object = s3Client.getObject(new GetObjectRequest(bucketName, defaultFilePath));
                return defaultS3Object.getObjectContent();
            } else {
                System.err.println(ex.getErrorMessage() + ": " + fileName);
            }
        }
        return null;
    }

    public static BlogPost getBlogPostFromS3(String objectKey) {
        try {
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, objectKey));
            InputStream inputStream = s3Object.getObjectContent();

            try {
                XWPFDocument document = new XWPFDocument(inputStream);
                String title = document.getParagraphs().get(0).getText();
                String byLine = document.getParagraphs().get(1).getText();
                List<String> content = new ArrayList<>();

                for (int i = 2; i < document.getParagraphs().size(); i++) {
                    content.add(document.getParagraphs().get(i).getText());
                }

                // Close the input stream
                inputStream.close();

                BlogPost blogPost = new BlogPost();

                // Get the post ID using regex pattern
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(objectKey);

                if (matcher.find()) {
                    blogPost.setPostId(Integer.parseInt(matcher.group()));
                }

                blogPost.setTitle(title);
                blogPost.setFileName(transformToLowerCaseWithDash(title));
                blogPost.setContent(content);
                blogPost.setByLine(byLine);

                return blogPost;
            } catch (IOException e) {
                e.printStackTrace();
                return null; // Return null in case of IOException
            }
        } catch (AmazonS3Exception ex) {
            System.err.println(ex.getErrorMessage());
            System.err.println(objectKey);
            System.exit(1); // Consider a more graceful way to handle this error
        }

        return null; // Return null for any other exceptions
    }


    public static List<BlogPost> getAllBlogPostsFromS3() {
        String prefix = "resources/blogPages"; // Specify the prefix you want to list

        List<BlogPost> blogPosts = new ArrayList<>();
        ListObjectsV2Request listRequest = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result result = s3Client.listObjectsV2(listRequest);
        for (int i=1; i< result.getObjectSummaries().size(); i++) {
            String objectKey = result.getObjectSummaries().get(i).getKey();
            blogPosts.add(getBlogPostFromS3(objectKey));
        }
        Comparator<BlogPost> idComparator = Comparator.comparingInt(BlogPost::getPostId).reversed();

        blogPosts.sort(idComparator);

        return blogPosts;
    }

    public static String transformToLowerCaseWithDash(String input) {
        if (input == null) {
            return "";
        }

        return input.replaceAll("\\s+", "-").toLowerCase().replaceAll("-+", "-").replaceAll("^-+|-+$", "");
    }
}

