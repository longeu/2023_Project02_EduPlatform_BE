package com.kits_internship.edu_flatform.validation;

import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

@Component
public class FileValidation {
    public void validateFiles(List<MultipartFile> multipartFile) {
        Map<String, Object> errors = new HashMap<>();
        String pattern = ("[^\\s]+(.*?)\\.(jpg|png|JPG|PNG|pdf|PDF)$");
        if (multipartFile.stream().anyMatch(x -> x.getName().isBlank())) {
            errors.put("files", "files must have a exist!");
            throw new UnprocessableEntityException(errors);
        }
        val checkName = multipartFile.stream().anyMatch(x -> x.getOriginalFilename().matches(pattern));
        if (!checkName) {
            errors.put("files", "file name not correct");
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
        final int MAX_FILE_SIZE = 20971520; //   10485760 - 10MB , 20971520 - 20MB
        if (total > MAX_FILE_SIZE) {
            errors.put("files", "files size is over 20MB!");
            throw new UnprocessableEntityException(errors);
        }
    }

}
