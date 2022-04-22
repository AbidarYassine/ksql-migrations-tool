package ma.octo.ksqlmigrations.dao.facade;

public interface AbstractDao<T, I> {

    T findById(I id);

    void save(T entity);


    void delete(I id);
}
