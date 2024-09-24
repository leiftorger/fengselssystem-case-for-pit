package no.leiftorger.fengselssystem_case_for_pit;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement

/*
 * denne ville normalt kun vært tilgjengelig under src/test, men vi bruker H2 for å kjøre appen som demo.
 */
public class DatabaseConfig {
	@Bean
	@Profile("!test")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.addScript("database/create.sql")
				.addScript("database/insert.sql")
				.build();
	}
}
