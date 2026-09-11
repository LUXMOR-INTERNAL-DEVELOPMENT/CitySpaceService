package app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:/sql.properties")
public class SqlPropertiesConfig {

}
