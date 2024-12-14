package com.travel.leave.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class ImageProcessor {
    private final static String FILE_PATH = "/히히_오줌발싸";

    public static String saveImage(String rawImage) {
        rawImage = rawImage.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(rawImage);

        String fileName = getUUID() + ".img";
        String filePath = FILE_PATH + fileName;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("섹스", e);
        }

        return filePath;
    }

    public static String getFileName(String filePath) {
        return filePath.substring(FILE_PATH.length());
    }

    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
