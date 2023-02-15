package pe.mil.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import pe.mil.microservices.utils.base.MicroserviceApplication;
import pe.mil.microservices.utils.components.properties.MicroserviceProperties;

import javax.validation.constraints.NotNull;

@EnableEurekaClient
@SpringBootApplication
public class Application extends MicroserviceApplication {

    public Application(@NotNull final MicroserviceProperties properties) {
        super(properties);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
