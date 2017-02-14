package fr.isima.dependencyinjector.transaction;

/**
 * Created by kernelith on 14/02/17.
 */
public class TransactionStatus
{
	private boolean isNewTransaction;

	/**
	 * Create a transaction status set to new transaction by default.
	 */
	public TransactionStatus()
	{
		this.isNewTransaction = true;
	}

	/**
	 * Create a transaction status with flag isNewTransaction set to parameter value.
	 * @param isNew IsNewTransaction flag
	 */
	public TransactionStatus(boolean isNew)
	{
		this.isNewTransaction = isNew;
	}

	/**
	 * Check if transaction is a new transaction.
	 * @return True if transaction is a new one.
	 */
	public boolean isNewTransaction()
	{
		return isNewTransaction;
	}
}
