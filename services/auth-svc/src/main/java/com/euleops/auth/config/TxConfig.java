package com.euleops.auth.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@EnableTransactionManagement
public class TxConfig {

    /** Transaction manager por defecto (bloqueante/JPA) */
    @Bean
    @Primary                                  // 👈 Soluciona la ambigüedad
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
