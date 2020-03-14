package it.transaction.atomikos.conf;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.transaction.jta.JtaTransactionManager;

public class AtomikosJtaPlatform extends AbstractJtaPlatform {

	private static TransactionManager transactionManager;
	private static UserTransaction userTransaction;

	@Override
	protected TransactionManager locateTransactionManager() {
		return transactionManager;
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		return userTransaction;
	}

	public void setJtaTransactionManager(JtaTransactionManager jtaTransactionManager) {
		transactionManager = jtaTransactionManager.getTransactionManager();
		userTransaction = jtaTransactionManager.getUserTransaction();
	}

}
