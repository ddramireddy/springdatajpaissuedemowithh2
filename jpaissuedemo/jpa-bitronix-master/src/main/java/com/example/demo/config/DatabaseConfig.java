package com.example.demo.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@EnableJpaRepositories
@Configuration
public class DatabaseConfig {

	public static final String PASSWORD = "password";
	public static final String USER = "user";
	public static final String SERVER_NAME = "serverName";
	public static final String PORT_NUMBER = "portNumber";
	public static final String DATABASE_NAME = "databaseName";
	public static final String SOCKET_TIMEOUT = "socketTimeout";
	public static final String CONNECTION_TEST_QUERY = "connection.test.query";
	public static final String SELECT_1 = "SELECT 1";
	String username = "postgres";
	String password = "postgres";

	@Bean(destroyMethod = "close")
	public DataSource globalConfigurationDataSource() {

		String url = "jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS test;DB_CLOSE_DELAY=-1";
		PoolingDataSource dataSource = new PoolingDataSource();
		dataSource.setClassName("org.h2.jdbcx.JdbcDataSource");
		dataSource.setUniqueName("jdbc/master");

		Properties datasourceProps = new Properties();
		datasourceProps.setProperty("url", url);
		/*
		datasourceProps.put(PASSWORD, password);
		datasourceProps.setProperty(USER, username);
		datasourceProps.setProperty(SERVER_NAME,
				getServerName(url));
		datasourceProps.setProperty(PORT_NUMBER, getPort(url).trim());


		datasourceProps.setProperty(DATABASE_NAME, getDBName(url));

		 */
		//datasourceProps.setProperty(SOCKET_TIMEOUT, "30");

		dataSource.setDriverProperties(datasourceProps);
		dataSource.setMaxIdleTime(3600);
		dataSource.setAllowLocalTransactions(true);
		dataSource.setShareTransactionConnections(true);
		dataSource.setMaxPoolSize(30);
		dataSource.setTestQuery(SELECT_1);
		dataSource.init();
		return dataSource;
	}

	public static String getPort(String url) {
		String[] splits = url.split("://")[1].split("/")[0].split(":");

		if (splits.length > 1)
			return splits[1];
		return "5432";

	}

	public static String getServerName(String url) {
		return url.split("://")[1].split("/")[0].split(":")[0];
	}

	public static String getDBName(String url) {
		return url.split("://")[1].split("/")[1];
	}

	@Bean(name = "bitronixTransactionManager")
	public BitronixTransactionManager bitronixTransactionManager() {
		return TransactionManagerServices.getTransactionManager();
	}

	@Bean(name = "transactionManager")
	@DependsOn({"bitronixTransactionManager"})
	public PlatformTransactionManager transactionManager(TransactionManager bitronixTransactionManager) {
		return new JtaTransactionManager(bitronixTransactionManager);
	}

	@Bean
	public PhysicalNamingStrategy physical() {
		return new PhysicalNamingStrategyStandardImpl();
	}

	@Bean
	public ImplicitNamingStrategy implicit() {
		return new ImplicitNamingStrategyLegacyJpaImpl();
	}

	@Bean
	public bitronix.tm.Configuration transactionManagerServices() {
		return TransactionManagerServices
				.getConfiguration();
	}

	@Bean(destroyMethod = "shutdown")
	public BitronixTransactionManager btmtransactionManager() {
		return TransactionManagerServices.getTransactionManager();
	}

	@Bean
	public UserTransaction userTransaction() {
		return TransactionManagerServices.getTransactionManager();
	}

}
