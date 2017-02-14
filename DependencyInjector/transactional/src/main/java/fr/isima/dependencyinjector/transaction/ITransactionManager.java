package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.annotations.Transactional.TransactionType;

/**
 * Created by kernelith on 14/02/17.
 */
public interface ITransactionManager
{
	void beginTransactionIfNecessary(TransactionType type);

	void commitTransactionAfterProceed();

	void rollbackTransactionAfterThrowing();
}
