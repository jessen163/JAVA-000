package com.jessen.demo;

import org.springframework.beans.factory.FactoryBean;

/**
 * 静态方法工厂
 */
public class QuestionFactoryV3 implements FactoryBean<Question> {

    public Question getObject() throws Exception {
        return new Question();
    }

    public Class<?> getObjectType() {
        return Question.class;
    }
}
