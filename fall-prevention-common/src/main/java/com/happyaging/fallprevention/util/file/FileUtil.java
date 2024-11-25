package com.happyaging.fallprevention.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.util.StringUtils;

public class FileUtil {
    private static final Tika tika = new Tika();
    private static final List<String> whiteImageExtensionList = Arrays.asList("image/jpeg",
            "image/pjpeg", "image/png", "image/gif", "image/bmp", "image/x-windows-bmp", "image/jpg");

    public static boolean isValidImageFile(InputStream inputStream) {
        try {
            String mimeType = tika.detect(inputStream);
            return whiteImageExtensionList.stream()
                    .anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String generateRandomUUIDString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String convertToRandomFilename(String filename) {
        String extension = StringUtils.getFilenameExtension(filename);
        String randomFilename = generateRandomUUIDString();
        return String.format("%s.%s", randomFilename, extension);
    }
}
