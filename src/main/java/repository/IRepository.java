package repository;

import java.util.Collection;

//----------------------------------------------------Interface IRepository----------------------------------------------------//
public interface IRepository <ID, T>{
    T add(T elem);
    void delete(T elem);
    void update (ID id, T elem);
    T findById (ID id);
    Iterable<T> findAll();
    Collection<T> getAll();
}
