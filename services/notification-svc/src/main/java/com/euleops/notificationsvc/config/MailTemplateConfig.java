package com.euleops.notificationsvc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class MailTemplateConfig {

    @Bean
    public ClassLoaderTemplateResolver mailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");          // carpeta dentro de resources
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(false);              // true en prod si quieres cachear
        return resolver;
    }

    @Bean
    public TemplateEngine mailTemplateEngine(ClassLoaderTemplateResolver mailTemplateResolver) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(mailTemplateResolver);
        return engine;
    }
}
