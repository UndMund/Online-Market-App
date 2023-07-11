package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
@RequiredArgsConstructor
public class ImageMapper {
    public String toString(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        return multipartFile.getOriginalFilename();
    }
}
