package repository;

import model.Identifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository <ID, T extends Identifiable<ID>> implements IRepository<ID, T>{
    protected Map<ID,T> elem;
    public AbstractRepository(){
        elem = new HashMap<>();
    }

    @Override
    public T add(T el){
        if(elem.containsKey(el.getId()))
            throw new RepositoryException("\nElement already exists\n");
        elem.put(el.getId(),el);
        return el;
    }

    @Override
    public void delete(T el){
        if(elem.containsKey(el.getId()))
            elem.remove(el.getId());
        else
            throw new RepositoryException("\nElement not in the database\n");
    }

    @Override
    public void update(ID id, T el){
        if(elem.containsKey(id))
            elem.put(el.getId(),el);
        else
            throw new RepositoryException("\nElement not in the database\n");
    }

    @Override
    public T findById(ID id){
        if(elem.containsKey(id))
            return elem.get(id);
        else
            return null;
    }

    @Override
    public Iterable<T> findAll(){
        return elem.values();
    }

    @Override
    public Collection<T> getAll(){
        return elem.values();
    }
}
