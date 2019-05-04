package com.foxminded.univer.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesHolderTest {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();

    @Test
    public void test() {
        String actualUrl = propertiesHolder.getUrl();
        String actualUser = propertiesHolder.getUser();
        String actualPassword = propertiesHolder.getPassword();
        assertEquals("jdbc:postgresql://localhost:5432/university", actualUrl);
        assertEquals("postgres", actualUser);
        assertEquals("123tester123", actualPassword);
    }
}
