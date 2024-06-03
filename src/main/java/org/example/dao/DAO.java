package org.example.dao;

import java.util.List;

public interface DAO<T, K> {
    T get(K id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(K id);
}
