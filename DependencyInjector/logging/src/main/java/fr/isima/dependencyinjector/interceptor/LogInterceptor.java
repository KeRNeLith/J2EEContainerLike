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
    @Inject
    private ILogger logger;

    @Override
    public Object invoke(InvocationContextChain invocation)
    {
        logger.add(invocation.getMethod().getName());

        return invocation.execNextInterceptor();
    }
}
