package ma.octo.ksqlmigrations.exceptions;

public class KsqlMigrationFileNotFound extends RuntimeException {
    public KsqlMigrationFileNotFound(String message) {
        super(message);
    }

}
