package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class InitializeDatabase {

    @Value("${constants.sql-schema}")
    private String schemaSqlPath;
    //"src/main/resources/schema.sql"

    private final DatabaseClient databaseClient;

    public InitializeDatabase(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @PostConstruct
    public void initializeDatabase() throws IOException {
        String schema = Files.readString(Path.of(schemaSqlPath));

        databaseClient.sql(schema).then().subscribe();
    }

    @PreDestroy
    public void clearDatabase(){
        databaseClient.sql("DROP TABLE IF EXISTS reservations CASCADE").then().subscribe();
        databaseClient.sql("DROP TABLE IF EXISTS users").then().subscribe();
        databaseClient.sql("DROP TABLE IF EXISTS rooms CASCADE").then().subscribe();
    }
}
