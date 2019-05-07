package com.foxminded.univer.dao;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import com.foxminded.univer.dao.impl.StudentDao;

public class JdbcDaoTest {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    StudentDao sd = new StudentDao();
    
    @Test
    public void test() throws ClassNotFoundException {
      Connection c = sd.getConnection();
      
      System.out.println(c.toString());
    }

}
