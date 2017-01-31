/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.logging;

import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.interceptor.IInterceptor;
import java.lang.reflect.Method;

/**
 *
 * @author alraberin1
 */
public class LogInterceptor implements IInterceptor
{
    @Inject
    private ILogger logger;

    @Override
    public void before(Object instance, Method method, Object... parameters) 
    {
        logger.add(method.getName());
    }

    @Override
    public void after(Object instance, Method method, Object... parameters) 
    {
        // Nothing
    }

    @Override
    public void onError(Object instance, Method method, Object... parameters) 
    {
        // Nothing
    }
}
