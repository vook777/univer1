package com.foxminded.univer.dao;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

    Collection<T> findAll();

    Optional<T> findById(Integer id);

    T save(T entity);

    void delete(T entity);
}
