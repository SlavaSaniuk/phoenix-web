package integrations.beans;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestDataSource {

    @Bean("testDS")
    @Profile("TEST")
    public DataSource testDataSource() {
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:~/Projects/H2/phoenix");
        ds.setUsername("phoenix-web");
        ds.setPassword("12345678");

        return ds;
    }
}
