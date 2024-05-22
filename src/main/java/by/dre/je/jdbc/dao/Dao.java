package by.dre.je.jdbc.dao;

import java.util.List;

public interface Dao<K, E> {
    boolean update(E e);
    E findById(K id);
    List<E> findAll();
    E save(E e);
    boolean delete(K id);
}
