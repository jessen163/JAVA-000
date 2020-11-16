package com.jessen.demo;

/**
 * 静态方法工厂
 */
public class QuestionFactory {
    public static Question getQuestionFactory() {
        return new Question();
    }
}
