package com.toyin.iwetoyin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUploadUtil {

    public static void saveFile(MultipartFile file) throws IOException {
        if (file != null) {
            byte[] bytes = file.getBytes();
            String modifiedFileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s", "-");
            System.out.println(modifiedFileName);
            Path path = Paths.get("src/main/resources/static/images/blogPhotos/" + modifiedFileName);
            Files.write(path, bytes);
        }
    }
}
