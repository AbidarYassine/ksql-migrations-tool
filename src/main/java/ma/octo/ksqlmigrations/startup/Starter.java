package ma.octo.ksqlmigrations.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.ksqlmigrations.dao.facade.MigrationDao;
import ma.octo.ksqlmigrations.dao.impl.MigrationDaoImpl;
import ma.octo.ksqlmigrations.db.GetKsqlClient;
import ma.octo.ksqlmigrations.entities.Migration;
import ma.octo.ksqlmigrations.exceptions.KsqlMigrationFileNotFound;
import ma.octo.ksqlmigrations.kasql.KsqlApi;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
public class Starter {

    private final KsqlApi ksqlApi;
    private final MigrationDao migrationDao;
    private static final String KSQL_MIGRATIONS_PATH = "src/main/resources/migrations";

    public Starter() {
        ksqlApi = new KsqlApi(GetKsqlClient.getKsqlClient());
        migrationDao = new MigrationDaoImpl();
    }


    public void run() {
        final File folder = new File(KSQL_MIGRATIONS_PATH);
        if (!folder.exists()) return;
        processAllFiles(folder);
    }

    public void processAllFiles(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            processFile(fileEntry);
        }
    }

    private void processFile(File fileEntry) {
        String content = getContentOfFile(fileEntry.getName());
        if (migrationDao.findByFileName(fileEntry.getName()).isEmpty()) {
            ksqlApi.executeStatement(content);
            addExecutedFileToDb(fileEntry.getName(), content);
        } else {
            String newHash = getHash(content);
            if (migrationDao.findByHashFile(newHash).isEmpty()) {
                log.info("FileCHanged : {}", fileEntry.getName());
            } else {
                log.info("File not changed : {}", fileEntry.getName());
            }
        }

    }

    private void addExecutedFileToDb(String name, String content) {
        Migration migration = new Migration();
        setData(migration, name, content);
        migrationDao.save(migration);
    }

    private void setData(Migration entity, String name, String content) {
        entity.setFileName(name);
        entity.setFileContent(content);
        String hash = getHash(content);
        entity.setHashFile(hash);
        entity.setVersion(name.split("__")[0]);
    }

    private String getHash(String content) {
        return DigestUtils.md5Hex(content).toUpperCase();
    }

    public String getContentOfFile(String fileName) {
        log.info("processing file : {}", fileName);
        InputStream inputStream = Starter.class.getResourceAsStream(String.format("/%s/%s", getKsqlFolderName(), fileName));
        if (inputStream == null) throw new KsqlMigrationFileNotFound("file not found");
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuilder sb = new StringBuilder();
        String str;
        try {
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            log.error("error while reading file : {}", fileName);
        }
        return sb.toString();
    }

    public String getKsqlFolderName() {
        String[] split = KSQL_MIGRATIONS_PATH.split("/");
        return split[split.length - 1];
    }

}


