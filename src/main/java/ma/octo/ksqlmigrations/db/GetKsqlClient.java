package ma.octo.ksqlmigrations.db;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ClientOptions;

public final class GetKsqlClient {
    private GetKsqlClient() {
    }

    private static Client INSTANCE;

    public static Client getKsqlClient() {
        if (INSTANCE == null) {
            ClientOptions options = ClientOptions.create()
                    .setHost("localhost")
                    .setPort(8088);
            INSTANCE = Client.create(options);
        }
        return INSTANCE;
    }
}
