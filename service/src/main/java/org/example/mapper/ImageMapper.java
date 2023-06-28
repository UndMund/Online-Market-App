package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.service.ImageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
@RequiredArgsConstructor
public class ImageMapper {
    private final ImageService imageService;
    public String toString(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        return multipartFile.getOriginalFilename();
    }

    public byte[] toMultipartFile(String fileName) {
        return imageService.findAvatar(fileName);
    }
}
