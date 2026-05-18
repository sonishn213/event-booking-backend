package com.projects.eventticket.eventticket.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public  class HtmlUtil {

    private final ResourceLoader resourceLoader;

    public String parse(String path, Map<String,String> payload) throws IOException {
        // Accesses src/main/resources/
        Resource resource = resourceLoader.getResource("classpath:email/" + path);
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        for(Map.Entry<String,String> entry : payload.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            content = content.replace("{"+key+"}",value);
        }

        return content;
    }
}
