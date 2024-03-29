/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.interceptor;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.injector.handlers.InvocationContextChain;
import fr.isima.dependencyinjector.logging.ILogger;

/**
 *
 * @author kernelith
 */
public class LogInterceptor implements IInterceptor
{
    /**
     * Logger.
     */
    @Inject
    private ILogger logger;

    /**
     * Handle logging interceptor logic.
     * @param invocation Context of invocation.
     * @return Bean method result.
     */
    @Override
    public Object invoke(InvocationContextChain invocation)
    {
        // Log method call
        logger.add(invocation.getMethod().getName());

        // Continue responsibility chain
        Object ret = invocation.execNextInterceptor();

        logger.add(invocation.getMethod().getName());

        return ret;
    }
}
