package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional.TransactionType;

import java.util.Stack;

/**
 * Created by kernelith on 14/02/17.
 */
public class TransactionManager implements ITransactionManager
{
	@Inject
	private ITransaction transcation;

	private static ThreadLocal<Stack<TransactionStatus>> callStackTransactions = new ThreadLocal<Stack<TransactionStatus>>() {
		@Override
		protected Stack<TransactionStatus> initialValue()
		{
			return new Stack<>();
		}
	};

	private boolean hasTransactionRunning()
	{
		return !callStackTransactions.get().empty();
	}

	private void pushTransaction(boolean isNew)
	{
		callStackTransactions.get().push(new TransactionStatus(isNew));
	}

	/**
	 * Pop last transaction if there is one.
	 * @return True if transaction is new, otherwise false.
	 */
	private boolean popTransaction()
	{
		boolean ret = false;
		if (hasTransactionRunning())
		{
			ret = callStackTransactions.get().peek().isNewTransaction();
			callStackTransactions.get().pop();
		}

		return ret;
	}

	@Override
	public void beginTransactionIfNecessary(TransactionType type)
	{
		if (!hasTransactionRunning() || type == TransactionType.REQUIRE_NEW)
		{
			transcation.begin();
			pushTransaction(true);
		}
		else
		{
			pushTransaction(false);
		}
	}

	@Override
	public void commitTransactionAfterProceed()
	{
		if (popTransaction())
		{
			transcation.commit();
		}
	}

	@Override
	public void rollbackTransactionAfterThrowing()
	{
		if (popTransaction())
		{
			transcation.rollback();
		}
	}
}
