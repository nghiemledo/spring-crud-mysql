package com.tutorial.apidemo.Springboot.tutorial.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService {
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException eception) {
            throw new RuntimeException("Cannot initialize storage",eception);
        }

    }
    private boolean isImageFile(MultipartFile file) {
        // get file path
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if(file.isEmpty()) throw new RuntimeException("Failed to store empty file");
            // check file is image
            if(!isImageFile(file)) throw new RuntimeException("You can only upload image file");
            // file must be <= 5Mb
            float fileSize = file.getSize() / 1_000_000;
            if(fileSize > 5.0f) throw new RuntimeException("File must be <= 5Mb");
            // File must be rename, why?
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generateFileName = UUID.randomUUID().toString().replace("-", "");
            generateFileName = generateFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generateFileName))
                    .normalize().toAbsolutePath();

            // security check
            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }

            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generateFileName;
        }
         catch (IOException e) {
                throw new RuntimeException("Failed to store empty file", e);
         }

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            // list all files in storage folder
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        }
         catch (IOException e) {
            throw new RuntimeException("Failed to load all files", e);
         }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = this.storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException("Could not read file " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file " + fileName, e);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
