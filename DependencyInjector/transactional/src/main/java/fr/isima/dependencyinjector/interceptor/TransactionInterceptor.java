/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.interceptor;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional.TransactionType;
import fr.isima.dependencyinjector.injector.handlers.InvocationContextChain;
import fr.isima.dependencyinjector.transaction.ITransaction;

import java.util.Stack;

/**
 *
 * @author kernelith
 */
public class TransactionInterceptor implements IInterceptor
{
    @Inject
    private ITransaction transcation;

    private static ThreadLocal<Stack<TransactionType>> callStackTransactions = new ThreadLocal<Stack<TransactionType>>() {
        @Override
        protected Stack<TransactionType> initialValue()
        {
            return new Stack<>();
        }
    };

    /*@Override
    public void before(Object instance, Method method, Object... parameters) 
    {
        if (method.isAnnotationPresent(Transactional.class))
        {
            Transactional transactionAnnotation = method.getAnnotation(Transactional.class);

            Stack<TransactionType> stackTransactions = callStackTransactions.get();
            // There is already transactions currently running => Determine if we need to open a new one
            if (!stackTransactions.empty())
            {
                // Only open a new transaction when there is a require new
                if (transactionAnnotation.type() == TransactionType.REQUIRE_NEW)
                {
                    transcation.begin();
                }
            }
            // No transaction open => open one
            else
            {
                transcation.begin();
            }

            callStackTransactions.get().push(transactionAnnotation.type());
        }
    }

    @Override
    public void after(Object instance, Method method, Object... parameters) 
    {
        // End transaction
        if (method.isAnnotationPresent(Transactional.class))
        {
            Stack<TransactionType> stackTransactions = callStackTransactions.get();
            TransactionType curTransactionType = stackTransactions.peek();
            stackTransactions.pop();

            // End of require new transaction or end of REQUIRE transaction (No other REQUIRE running) => commit it
            if (curTransactionType == TransactionType.REQUIRE_NEW
                || (curTransactionType == TransactionType.REQUIRE && stackTransactions.search(TransactionType.REQUIRE) == -1))
            {
                transcation.commit();
            }
        }
    }
    
    @Override
    public void onError(Object instance, Exception exception, Method method, Object... parameters) throws Exception
    {
        // Error during transaction
        if (method.isAnnotationPresent(Transactional.class))
        {
            callStackTransactions.get().pop();
            transcation.rollback();
        }

        if (exception != null)
            throw exception;
    }*/

    @Override
    public Object invoke(InvocationContextChain invocation)
    {
        Object ret = null;

        transcation.begin();

        try
        {
            ret = invocation.execNextInterceptor();
            transcation.commit();
        }
        catch (Exception e)
        {
            transcation.rollback();

            // Re-throw exception
            throw e;
        }

        return ret;
    }
}
