package com.travel.leave.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class ImageProcessor {
    private final static String FILE_PATH = "/히히_오줌발싸";

    public static String saveImage(String rawImage) {
        rawImage = rawImage.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(rawImage);

        String extension = rawImage.split(";")[0].split("/")[1];
        String fileName = getUUID() + "." + extension;
        String filePath = FILE_PATH + fileName;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("섹스", e);
        }

        return filePath;
    }

    public static void deleteImage(String filePath) {
        File file = new File(filePath);
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("파일 삭제 실패: " + filePath);
        }
    }

    public static String updateImage(String oldFilePath, String rawImage) {
        deleteImage(oldFilePath);
        return saveImage(rawImage);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}