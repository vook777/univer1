package com.foxminded.univer.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesHolderTest {

    @Test
    public void returnsCorrectPrametersTest() {
        String actualUrl = PropertiesHolder.URL;
        String actualUser = PropertiesHolder.USER;
        String actualPassword = PropertiesHolder.PASSWORD;
        assertEquals("jdbc:postgresql://localhost:5432/dbunit", actualUrl);
        assertEquals("postgres", actualUser);
        assertEquals("123tester123", actualPassword);
    }
}
