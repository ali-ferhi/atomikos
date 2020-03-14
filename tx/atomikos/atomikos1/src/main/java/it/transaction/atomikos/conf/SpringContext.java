package it.transaction.atomikos.conf;

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
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = { "it.transaction.atomikos.dao", "it.transaction.atomikos.business" })
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
		em.setPackagesToScan(new String[] { "it.transaction.atomikos.entities." + schema });
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
	
	@Bean(name = "atomikosJtaPlatform")
	public AtomikosJtaPlatform atomikosJtaPlatform() {
		AtomikosJtaPlatform toReturn = new AtomikosJtaPlatform();
		toReturn.setJtaTransactionManager(jtaTxManager());
		return toReturn;
	}

	public static Properties jpaProperties(String schema) {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.default_schema", schema);
		//properties.setProperty("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.SunOneJtaPlatform");
		properties.setProperty("hibernate.transaction.jta.platform", "it.transaction.atomikos.conf.AtomikosJtaPlatform");
		return properties;
	}
	
/*	@Bean(name = "amqConnectionFactory")
	public ActiveMQXAConnectionFactory amqConnectionFactory() {
		ActiveMQXAConnectionFactory toReturn = new ActiveMQXAConnectionFactory();
		toReturn.setBrokerURL("tcp://localhost:61616");
		toReturn.setUserName("admin");
		toReturn.setPassword("admin");
		return toReturn;
	}*/
	
	@Bean(name = "atomikosTxManager")
	public UserTransactionManager atomikosTxManager() {
		UserTransactionManager toReturn = new UserTransactionManager();
		return toReturn;
	}
	
	@Bean(name = "atomikosUserTx")
	public UserTransactionImp atomikosUserTx() {
		UserTransactionImp toReturn = new UserTransactionImp();
		return toReturn;
	}
	
	@Bean(name = "jtaTxManager")
	public JtaTransactionManager jtaTxManager() {
		JtaTransactionManager toReturn = new JtaTransactionManager();
		toReturn.setTransactionManager(atomikosTxManager());
		toReturn.setUserTransaction(atomikosUserTx());
		return toReturn;
	}

}
