package app.groopy.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Imposta l'origine consentita (puoi specificare l'URL del client o "*" per consentire da qualsiasi origine)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Imposta i metodi HTTP consentiti
                .allowedHeaders("*") // Imposta gli header consentiti
                .allowCredentials(true) // Abilita l'invio di credenziali (se necessario)
                .maxAge(3600); // Imposta il tempo di memorizzazione nella cache della configurazione CORS
    }
}