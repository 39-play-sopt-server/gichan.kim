package org.sopt.storage.db.core;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackages = "org.sopt.storage.db.core")
@EnableJpaRepositories(basePackages = "org.sopt.storage.db.core")
public class CoreJpaConfig {
}
