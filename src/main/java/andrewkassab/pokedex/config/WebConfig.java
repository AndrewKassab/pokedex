package andrewkassab.pokedex.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class WebConfig {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
            registry.addErrorPages(error404Page);
        };
    }

}
