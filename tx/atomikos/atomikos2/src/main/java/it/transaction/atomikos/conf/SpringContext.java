package it.transaction.atomikos.conf;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueSession;
import javax.sql.DataSource;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.connection.JmsTransactionManager;
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
	public AtomikosJtaPlatform atomikosJtaPlatform() throws IllegalStateException, RollbackException, SystemException, JMSException {
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
	
	public ActiveMQConnectionFactory amqueueConnectionFactory() {
		ActiveMQConnectionFactory toReturn = new ActiveMQConnectionFactory();
		return toReturn;
	}
	
	@Bean(name = "amqConnectionFactory")
	public ActiveMQXAConnectionFactory amqXaQueueConnectionFactory() {
		ActiveMQXAConnectionFactory toReturn = new ActiveMQXAConnectionFactory();
		toReturn.setBrokerURL("tcp://localhost:61616");
		toReturn.setUserName("admin");
		toReturn.setPassword("admin");
		return toReturn;
	}
	
	@Bean(name = "amqXaConnection")
	public XAQueueConnection amqXaQueueConnection() throws JMSException {
		XAQueueConnection toReturn = amqXaQueueConnectionFactory().createXAQueueConnection();
		return toReturn;
	}
	
	@Bean(name = "amqXaQueueSession")
	public XAQueueSession amqXaQueueSession() throws JMSException {
		XAQueueSession toReturn = amqXaQueueConnection().createXAQueueSession();
		return toReturn;
	}
	
	@Bean(name = "amqQueue")
	public Queue amqQueue() throws JMSException {
		Queue toReturn = amqXaQueueSession().createQueue("input");
		return toReturn;
	}
	
	@Bean(name = "amqMsgProducer")
	public MessageProducer amqMsgProducer() throws JMSException {
		MessageProducer toReturn = amqXaQueueSession().createProducer(amqQueue());
		return toReturn;
	}
	
	@Bean(name = "jmsTxManager")
	public JmsTransactionManager jmsTxManager()  {
		JmsTransactionManager toReturn = new JmsTransactionManager();
		return toReturn;
	}
	
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
	public JtaTransactionManager jtaTxManager() throws IllegalStateException, RollbackException, SystemException, JMSException {
		JtaTransactionManager toReturn = new JtaTransactionManager();
		toReturn.setTransactionManager(atomikosTxManager());
		toReturn.setUserTransaction(atomikosUserTx());
		toReturn.getTransactionManager().getTransaction().enlistResource(amqXaQueueSession().getXAResource());
		return toReturn;
	}

}
