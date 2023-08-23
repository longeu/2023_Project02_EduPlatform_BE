package com.kits_internship.edu_flatform.ulti;


import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import lombok.val;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.LongStream;

@Component
public class FileUtils {
    public void validateFiles(List<MultipartFile> multipartFile) {
        Map<String, Object> errors = new HashMap<>();
        String pattern = ("[^\\s]+(.*?)\\.(jpg|png|JPG|PNG|pdf|PDF|MP4|mp4|avi|AVI|flv|FLV|xlsx|XLSX|docx|DOCX)$");
        if (multipartFile.stream().anyMatch(x -> x.getName().isBlank())) {
            errors.put("files", "files must have a exist!");
            throw new UnprocessableEntityException(errors);
        }
        val checkName = multipartFile.stream().anyMatch(x -> x.getOriginalFilename().matches(pattern));
        if (!checkName) {
            errors.put("files", "file undefined!");
            throw new UnprocessableEntityException(errors);
        }
        long total = multipartFile.stream().flatMapToLong(x -> {
            try {
                return LongStream.of(x.getBytes().length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return LongStream.of(0);
        }).sum();
        final long MAX_IMAGE_SIZE = 10485760; //   10485760 - 10MB , 20971520 - 20MB
        final long MAX_VIDEO_SIZE = 150000000; // 150000000 - 150MB
        if (multipartFile.get(0).getContentType().startsWith("image") && total > MAX_IMAGE_SIZE) {
            errors.put("files", "images size is over 10MB!");
            throw new UnprocessableEntityException(errors);
        }
        if (multipartFile.get(0).getContentType().startsWith("video") && total > MAX_VIDEO_SIZE) {
            errors.put("files", "videos size is over 150MB!");
            throw new UnprocessableEntityException(errors);
        }
    }

    public String uploadVideo(File file, String fileName) throws IOException {
        UUID uuid = UUID.randomUUID();
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", uuid.toString());

        BlobId blobId = BlobId.of("edu-platform-e3877.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType(getExtension(fileName)).build();
        Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource("eduPlatform-accountKey.json").getInputStream());

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        StringBuilder stringBuilder = new StringBuilder("https://firebasestorage.googleapis.com/v0/b/edu-platform-e3877.appspot.com/o/");
        stringBuilder.append(URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "?alt=media&token=" + uuid);
        return stringBuilder.toString();
    }

    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
