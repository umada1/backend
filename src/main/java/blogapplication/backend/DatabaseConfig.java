package blogapplication.backend;


import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

// this is a standard database configuration file - helper that was made following an example of online resources.

@Configuration
public class DatabaseConfig {

  @Value("${spring.datasource.url}")
  
  private String databaseAddress;

  @Bean
  public DataSource dataSource() {
      HikariConfig configuration = new HikariConfig();
      configuration.setJdbcUrl(databaseAddress);
      return new HikariDataSource(configuration);
  }
}