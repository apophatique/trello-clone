package com.github.hu553in.trello_clone.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.media.ObjectSchema;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiCustomizer {
    @Bean
    public OpenApiCustomiser openApiCustomiser(final ObjectMapper objectMapper) {
        return openApi -> {
            var errorMapSchema = new ObjectSchema();
            errorMapSchema.name("ErrorMap");
            errorMapSchema.example(objectMapper.createObjectNode().put("field name", "error message"));
            openApi.getComponents().getSchemas().put(errorMapSchema.getName(), errorMapSchema);
        };
    }
}
