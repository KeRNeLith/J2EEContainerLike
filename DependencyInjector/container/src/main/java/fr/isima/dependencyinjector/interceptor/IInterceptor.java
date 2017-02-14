/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.interceptor;

import fr.isima.dependencyinjector.injector.handlers.InvocationContextChain;

/**
 *
 * @author kernelith
 */
public interface IInterceptor 
{
    /**
     * Handle interceptor logic.
     * @param invocation Context of invocation.
     * @return Interceptor result.
     */
    Object invoke(InvocationContextChain invocation);
}
