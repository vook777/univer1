package com.foxminded.univer.dao;

import java.util.List;

public interface CrudDao<T> {

    T create(T t);

    T update(T t);

    boolean delete(T t);

    T findById(Integer id);

    List<T> findAll();
}
