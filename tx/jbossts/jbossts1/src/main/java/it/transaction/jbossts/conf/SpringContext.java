package it.transaction.jbossts.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = { "it.transaction.jbossts.dao", "it.transaction.jbossts.business" })
public class SpringContext {

	@Bean(name = "entityManager1")
	public LocalContainerEntityManagerFactoryBean entityManager1() {
		return entityManagerFactory("schema1", "unitName1");
	}

	
	 @Bean(name = "entityManager2")
	 public LocalContainerEntityManagerFactoryBean entityManager2() { 
		 return entityManagerFactory("schema2", "unitName2");
	 }
	
	public static LocalContainerEntityManagerFactoryBean entityManagerFactory(String schema, String unitName) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setJtaDataSource(dataSource());
		em.setJpaProperties(jpaProperties(schema));
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
	
	/*public static DataSource jtaDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/database1");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		dataSource.setDriverClassName("com.arjuna.ats.jdbc.TransactionalDriver");
		dataSource.setConnectionProperties(dataSourceConnectionProperties());
		return dataSource;
	}*/

	public static Properties jpaProperties(String schema) {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.default_schema", schema);
		return properties;
	}
	
	/*public static Properties dataSourceConnectionProperties() {
		Properties toReturn = new Properties();
		toReturn.setProperty("DYNAMIC_CLASS", "com.arjuna.ats.internal.jdbc.drivers.PropertyFileDynamicClass");
		return toReturn;
	}*/
	
	@Bean(name = "jbossTransactionManager")
	public TransactionManagerImple jbossTransactionManager() {
		TransactionManagerImple toReturn = new TransactionManagerImple();
		return toReturn;
	}
	
	@Bean(name = "jbossUserTransaction")
	public UserTransactionImple jbossUserTransaction() {
		UserTransactionImple toReturn = new UserTransactionImple();	
		return toReturn;
	}
	
	@Bean(name = "transactionManager")
	public JtaTransactionManager transactionManager() {
		JtaTransactionManager toReturn = new JtaTransactionManager();
		toReturn.setTransactionManager(jbossTransactionManager());
		toReturn.setUserTransaction(jbossUserTransaction());
		return toReturn;
	}

}
