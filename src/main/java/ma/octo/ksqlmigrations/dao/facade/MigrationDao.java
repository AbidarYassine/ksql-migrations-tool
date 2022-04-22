package ma.octo.ksqlmigrations.dao.facade;



import ma.octo.ksqlmigrations.entities.Migration;

import java.util.Optional;

public interface MigrationDao extends AbstractDao<Migration, Long> {

    Optional<Migration> findByFileName(String name);

    Optional<Migration>  findByHashFile(String newHash);
}
