package com.student.studentCrud.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class config {

    ModelMapper mapper = null;

    @Bean
    public ModelMapper getModelMapper() {
        return Objects.requireNonNullElseGet(mapper, ModelMapper::new);
    }
}
