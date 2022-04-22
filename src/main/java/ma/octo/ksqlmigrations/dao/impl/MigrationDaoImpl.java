package ma.octo.ksqlmigrations.dao.impl;


import lombok.extern.slf4j.Slf4j;
import ma.octo.ksqlmigrations.dao.facade.MigrationDao;
import ma.octo.ksqlmigrations.db.GetEntityMangerFactory;
import ma.octo.ksqlmigrations.entities.Migration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Optional;

@Slf4j
public class MigrationDaoImpl implements MigrationDao {

    private static final EntityManagerFactory entityManagerFactory = GetEntityMangerFactory.getEntityManagerFactory();



    @Override
    public Migration findById(Long id) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Migration migration = entityManager.find(Migration.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return migration;
    }

    @Override
    public void save(Migration entity) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    @Override
    public void delete(Long id) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Migration migration = entityManager.find(Migration.class, id);
        entityManager.remove(migration);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Optional<Migration> findByFileName(String name) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT m FROM Migration m WHERE m.fileName = :name");
        query.setParameter("name", name);
        try {
            Migration migration = (Migration) query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return Optional.ofNullable(migration);
        } catch (Exception e) {
            log.error("Error while finding migration by file name {}", name);
        }
        entityManager.close();
        return Optional.empty();

    }

    @Override
    public Optional<Migration> findByHashFile(String newHash) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT m FROM Migration m WHERE m.hashFile = :newHash");
        query.setParameter("newHash", newHash);
        try {
            Migration migration = (Migration) query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return Optional.ofNullable(migration);
        } catch (Exception e) {
            log.error("Error while finding migration by hash file {}", newHash);
        }
        entityManager.close();
        return Optional.empty();
    }
}
