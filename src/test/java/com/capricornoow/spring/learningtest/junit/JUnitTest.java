package com.capricornoow.spring.learningtest.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/junit.xml")
public class JUnitTest {
    @Autowired
    ApplicationContext context;

    static Set<JUnitTest> testObjects = new HashSet<>();
    static ApplicationContext contextObject = null;

    @Test
    public void test1 () {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == context, is(true));
        contextObject = context;
    }

    @Test
    public void test2 () {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        assertTrue(contextObject == null || contextObject == context);
        contextObject = context;
    }

    @Test
    public void test3 () {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        assertThat(contextObject, either(is(nullValue())).or(is(context)));
        contextObject = context;
    }
}
