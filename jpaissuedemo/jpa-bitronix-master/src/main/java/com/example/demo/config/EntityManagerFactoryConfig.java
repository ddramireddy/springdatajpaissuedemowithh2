package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(
    entityManagerFactoryRef = "db1EntityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = "com.example.demo.repository.primary"
)
@Lazy
public class EntityManagerFactoryConfig {

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}

	@Primary
	@Bean(name = "db1EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("globalConfigurationDataSource") DataSource dataSource) {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", BitronixJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");
		properties.put("hibernate.jdbc.lob.non_contextual_creation", true); // postgresql用
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(dataSource);
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager.setPackagesToScan("com.example.demo.entity");
		entityManager.setPersistenceUnitName("PrimaryDataBase");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
