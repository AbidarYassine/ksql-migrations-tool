package ma.octo.ksqlmigrations.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class GetEntityMangerFactory {
    private GetEntityMangerFactory() {
    }

    private static EntityManagerFactory INSTANCE;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (INSTANCE == null) {
            INSTANCE = Persistence.createEntityManagerFactory("ma.octo.ksql-migrations");
        }
        return INSTANCE;
    }
}
