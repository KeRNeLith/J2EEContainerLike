/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.interceptor;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional;
import fr.isima.dependencyinjector.injector.handlers.InvocationContextChain;
import fr.isima.dependencyinjector.transaction.ITransactionManager;

import java.lang.reflect.Method;

/**
 *
 * @author kernelith
 */
public class TransactionInterceptor implements IInterceptor
{
    @Inject
    private ITransactionManager transactionManager;

    @Override
    public Object invoke(InvocationContextChain invocation)
    {
        Object ret = null;

        Method method = invocation.getMethod();
        Transactional transactionalAnnotation = method.getAnnotation(Transactional.class);

        transactionManager.beginTransactionIfNecessary(transactionalAnnotation.type());
        try
        {
            ret = invocation.execNextInterceptor();
            transactionManager.commitTransactionAfterProceed();
        }
        catch (Exception e)
        {
            transactionManager.rollbackTransactionAfterThrowing();

            // Re-throw exception
            throw e;
        }

        return ret;
    }
}
