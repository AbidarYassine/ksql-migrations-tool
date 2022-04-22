package ma.octo.ksqlmigrations.kasql;

import io.confluent.ksql.api.client.BatchedQueryResult;
import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ExecuteStatementResult;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class KsqlApi {

    private final Client client;

    public CompletableFuture<ExecuteStatementResult> executeStatement(String sql) {
        return client.executeStatement(sql);
    }

    public BatchedQueryResult executeQuery(String sql) {
        return client.executeQuery(sql);
    }

}
