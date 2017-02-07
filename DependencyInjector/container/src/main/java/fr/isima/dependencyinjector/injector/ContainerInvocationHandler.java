/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.injector.interceptor.IInterceptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// NOT USDED FOR THE MOMENT
/**
 *
 * @author kernelith
 */
public class ContainerInvocationHandler implements InvocationHandler
{
    private Object m_object;

    //private IInterceptor interceptor;

    public ContainerInvocationHandler(Object object)
    {
        m_object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
    {
        Object ret = null;

        // Find annotations annoted behaviour
        //try
        {
            //interceptor.before(proxy, method, args);

            ret = method.invoke(m_object, args);

            //interceptor.after(proxy, method, args);
        }
        //finally
        {
            //interceptor.onError(proxy, method, args);
        }
        
        return ret;
    }

    public Object getObject()
    {
        return m_object;
    }
}
