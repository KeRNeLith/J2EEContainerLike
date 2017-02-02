/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.injector.interceptor.IInterceptor;
import java.lang.reflect.Method;
import javassist.util.proxy.MethodHandler;

/**
 *
 * @author kernelith
 */
public class ContainerMethodHandler implements MethodHandler
{
    private final IInterceptor interceptor;
    
    public ContainerMethodHandler(IInterceptor interceptor)
    {
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxyInstance, Method thisMethod, Method proceed, Object[] args) throws Throwable 
    {
        Object ret = null;
        
        try
        {
            interceptor.before(proxyInstance, thisMethod, args);

            ret = proceed.invoke(proxyInstance, args);

            interceptor.after(proxyInstance, thisMethod, args);
        }
        catch (Exception e)
        {
            interceptor.onError(proxyInstance, thisMethod, args);
        }
        
        return ret;
    }
}
