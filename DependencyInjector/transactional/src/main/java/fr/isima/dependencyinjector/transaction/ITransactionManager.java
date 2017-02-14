package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.annotations.Transactional.TransactionType;

/**
 * Created by kernelith on 14/02/17.
 */
public interface ITransactionManager
{
	/**
	 * Start a new transaction if needed (REQUIRE_NEW of REQUIRE when there is currently no transaction running).
	 * @param type Type of transaction to begin.
	 */
	void beginTransactionIfNecessary(TransactionType type);

	/**
	 * Commit transaction if it is the end of a running transaction marked as new.
	 */
	void commitTransactionAfterProceed();

	/**
	 * Rollback transaction if it part of a new transaction.
	 */
	void rollbackTransactionAfterThrowing();
}
