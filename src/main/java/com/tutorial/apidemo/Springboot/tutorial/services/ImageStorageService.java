package com.tutorial.apidemo.Springboot.tutorial.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Override
    public String storeFile(MultipartFile file) {
        if(file.isEmpty()) return null;
        return "";
    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {

    }
}
