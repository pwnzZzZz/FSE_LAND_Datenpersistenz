package dataaccess;

import java.util.List;
import java.util.Optional;

/**
 * Die Klasse gibt Standard CRUD-Methoden vor
 * und parametrisiert diese mit generischen Typen
 * Kann für alle weiteren Repositories verwendet werden
 */

public interface BaseRepository <T,I>{
    Optional<T> insert(T entity);
    Optional<T> getById(I id);
    List<T> getAll();
    Optional<T> update(T entity);
    void deleteById(I id);
}
