package com.jessen.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
//不写名字默认为App-context.xml
public class TestInitBean {
    @Autowired
    private ApplicationContext context;

    @Test
    public void testInitBean1(){
        Question bean1 = (Question) context.getBean("question1");
        assertNotNull(bean1);
    }
    @Test
    public void testInitBean2(){
        Question bean1 = (Question) context.getBean("question2");
        assertNotNull(bean1);
    }
    @Test
    public void testInitBean3(){
        Question bean1 = (Question) context.getBean("question3");
        assertNotNull(bean1);
    }
    @Test
    public void testInitBean4(){
        Question bean1 = (Question) context.getBean("question4");
        assertNotNull(bean1);
    }
    @Test
    public void testInitBean5(){
        Question bean1 = (Question) context.getBean("question5");
        assertNotNull(bean1);
    }
}
