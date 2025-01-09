package com.happyaging.fallprevention.storage.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.happyaging.fallprevention.storage.exception.FileExtensionUnsupportedException;
import com.happyaging.fallprevention.storage.exception.FileNotFoundException;
import com.happyaging.fallprevention.storage.exception.FilePathInvalidException;
import com.happyaging.fallprevention.storage.exception.FileStoreFailedException;
import com.happyaging.fallprevention.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageService {
    private static final Path storageLocation = Paths.get("/storage/images");

    public String storeImage(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                throw new FileNotFoundException();
            }
            if (!FileUtil.isValidImageFile(image.getInputStream())) {
                throw new FileExtensionUnsupportedException();
            }

            Files.createDirectories(storageLocation);

            String randomizedFilename =
                    FileUtil.convertToRandomFilename(image.getOriginalFilename());
            Path destination = storageLocation.resolve(Paths.get(randomizedFilename)).normalize()
                    .toAbsolutePath();
            if (!destination.getParent().equals(storageLocation.toAbsolutePath())) {
                throw new FilePathInvalidException();
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            return randomizedFilename;
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new FileStoreFailedException();
        }
    }

    private Path load(String fileName) {
        return storageLocation.resolve(fileName);
    }

    public Resource loadAsResource(String fileName) {
        try {
            Path path = load(fileName);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new FilePathInvalidException();
        }
    }
}
