package com.travel.leave.domain.ai_travel.util;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PromptLoader {

    public static String loadPrompt(String filePath) {
        try {
            Path path = ResourceUtils.getFile(filePath).toPath();
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("프롬프트 파일을 읽는 중 오류가 발생했습니다: " + filePath, e);
        }
    }
}
