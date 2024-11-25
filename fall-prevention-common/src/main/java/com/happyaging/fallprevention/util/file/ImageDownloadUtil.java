package com.happyaging.fallprevention.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ImageDownloadUtil {
	private static final Path downloadLocation = Paths.get("/storage/images");

	public static String downloadImage(String imageUrl) throws IOException {
		try {
			Resource resource = new UrlResource(imageUrl);
			if (!resource.exists() || !resource.isReadable()) {
				throw new IOException("Failed to read image from URL: " + imageUrl);
			}

			String fileName = FileUtil.convertToRandomFilename(imageUrl);
			Path destination = downloadLocation.resolve(fileName).normalize().toAbsolutePath();
			if (!destination.getParent().equals(downloadLocation.toAbsolutePath())) {
				throw new IOException("Invalid file path for image download.");
			}

			try (InputStream inputStream = resource.getInputStream()) {
				Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
			}

			return destination.getFileName().toString();
		} catch (MalformedURLException e) {
			throw new IOException("Malformed URL: " + imageUrl, e);
		}
	}
}
