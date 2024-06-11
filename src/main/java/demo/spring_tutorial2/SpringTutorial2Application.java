package demo.spring_tutorial2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringTutorial2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringTutorial2Application.class, args);
    }

}
