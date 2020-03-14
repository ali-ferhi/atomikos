package it.transaction.atomikos.business;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.transaction.atomikos.dao.DaoImpl;

@Service
public class BusinessImpl {
	
	@Autowired
	DaoImpl daoImpl;
	
	private ApplicationContext springContext;	

	
	@Transactional(transactionManager = "jtaTxManager")
	public void createInDbAndSendToJms(int id, String key, int value) throws JMSException {
		createInDb(id,key, value);
		if(true) {
			throw new RuntimeException("test");
		}
		sendMessageToJmsQueue("id = " + id + ", key = " + key + ", value = " + value);
	}
	
	@Transactional(transactionManager = "jtaTxManager")
	public void sendToJmsAndCreateInDb(int id, String key, int value) throws Exception {
		sendMessageToJmsQueue("id = " + id + ", key = " + key + ", value = " + value);
		if(true) {
			throw new RuntimeException("test");
		}		
		createInDb(id,key, value);
	}
	
	public void createInDb(int id, String key, int value) {
		daoImpl.createInSchema1(id, key, value);
	}
	
	public void sendMessageToJmsQueue(String msg) throws JMSException {
		TextMessage jmsMsg = new ActiveMQTextMessage();
		jmsMsg.setText(msg);
		MessageProducer msgProducer = (MessageProducer) this.springContext.getBean("amqMsgProducer");
		msgProducer.send(jmsMsg);
	}
	
	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}

}
