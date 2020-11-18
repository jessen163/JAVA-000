package com.jessen.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@ConditionalOnClass({Student.class, School.class, Klass.class})
@EnableConfigurationProperties(SpringBootPropertiesConfiguration.class)
@ConditionalOnProperty(prefix = "spring.custom-starter", name = "enabled", havingValue = "true", matchIfMissing = true)
@PropertySource("classpath:application.yml")
public class SpringBootConfiguration
{
    @Autowired
    private SpringBootPropertiesConfiguration configuration;

    @Bean
    public Klass klass() {
        List<Student> students = configuration.getStudents();
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean
    @Autowired
    public School school(Klass klass) {
        School school = new School();
        school.setClass1(klass);
        return school;
    }
}
