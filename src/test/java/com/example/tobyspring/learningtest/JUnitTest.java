package com.example.tobyspring.learningtest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitTest {

    static JUnitTest testObject;

    @Test
    void test1() {
        assertThat(this).isNotEqualTo(testObject);
        testObject = this;
    }

    @Test
    void test2() {
        assertThat(this).isNotEqualTo(testObject);
        testObject = this;
    }

    @Test
    void test3() {
        assertThat(this).isNotEqualTo(testObject);
        testObject = this;
    }
}
