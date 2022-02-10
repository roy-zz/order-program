package com.roy.order.base.configurations.flyway;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfiguration {

    public final DataSource dataSource;

    @Bean("flywayForOrderProgram")
    public Flyway flywayForOrderProgram() {
        Flyway flyway = Flyway.configure().baselineOnMigrate(Boolean.TRUE).dataSource(this.dataSource).locations("db/migration/orderprogram").load();

        return flyway;
    }

    static class OrderProgramFlywayMigrationInitializer extends FlywayMigrationInitializer {

        Flyway[] flyways;

        public OrderProgramFlywayMigrationInitializer(Flyway flyway, Flyway... flyways) {
            super(flyway);
            this.flyways = flyways;
        }

        public void migrate() {
            for (Flyway flyway : this.flyways) {
                flyway.migrate();
            }
        }
    }

    @Bean
    public FlywayMigrationInitializer initializeDBs(@Qualifier("flywayForOrderProgram") Flyway flywayForOrderProgram) {
        return new OrderProgramFlywayMigrationInitializer(flywayForOrderProgram);
    }
}
