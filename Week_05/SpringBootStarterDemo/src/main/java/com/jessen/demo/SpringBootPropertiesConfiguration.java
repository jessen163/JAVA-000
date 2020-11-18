package com.jessen.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.custom-starter")
public class SpringBootPropertiesConfiguration {
    private List<Student> students;
}
