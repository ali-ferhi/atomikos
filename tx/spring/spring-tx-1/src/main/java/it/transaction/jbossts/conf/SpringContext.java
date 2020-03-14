package it.transaction.jbossts.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = { "it.transaction.jbossts.dao", "it.transaction.jbossts.business" })
public class SpringContext {

	@Bean(name = "entityManager1")
	public LocalContainerEntityManagerFactoryBean entityManager1() {
		return entityManagerFactory("schema1", "unitName1");
	}

	
	 @Bean(name = "entityManager2")
	 public LocalContainerEntityManagerFactoryBean
	 	entityManager2() { return entityManagerFactory("schema2", "unitName2");
	 }
	 

	public static LocalContainerEntityManagerFactoryBean entityManagerFactory(String schema, String unitName) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setJpaProperties(jpalProperties(schema));
		em.setPersistenceUnitName(unitName);
		em.setPackagesToScan(new String[] { "it.transaction.jbossts.entities." + schema });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		return em;
	}

	public static DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/database1");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		dataSource.setDriverClassName("org.postgresql.Driver");
		return dataSource;
	}

	public static Properties jpalProperties(String schema) {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.default_schema", schema);
		return properties;
	}

	@Bean(name = "transactionManager1")
	public PlatformTransactionManager transactionManager1() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManager1().getObject());
		return transactionManager;
	}
	
	@Bean(name = "transactionManager2")
	public PlatformTransactionManager transactionManager2() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManager2().getObject());
		return transactionManager;
	}

}
