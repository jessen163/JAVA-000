package com.jessen.demo;

/**
 * 静态方法工厂
 */
public class QuestionFactoryV2 {
    public Question getQuestionFactory() {
        return new Question();
    }
}
