package com.foxminded.univer.dao;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

    Collection<T> findAll() throws ClassNotFoundException;

    Optional<T> findById(Integer id) throws ClassNotFoundException;

    T save(T entity) throws ClassNotFoundException;

    void delete(T entity) throws ClassNotFoundException;
}
