/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.handlers;

import fr.isima.dependencyinjector.injector.factories.ContainerObjectFactory;
import fr.isima.dependencyinjector.injector.finders.BehaviourFinder;
import fr.isima.dependencyinjector.injector.finders.ClassFinder;
import fr.isima.dependencyinjector.interceptor.IInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kernelith
 */
public class ContainerInvocationHandler implements InvocationHandler
{
    private Object instance;

    public ContainerInvocationHandler()
    {
        instance = null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
    {
        Object ret = null;

        // Check if object is already injected or not
        if (instance == null)
        {
            Class interfaceClass = method.getDeclaringClass();
            Class concreteClass = ClassFinder.findClassFor(interfaceClass);

            // Lazy injection
            instance = ContainerObjectFactory.createNewInstanceFor(concreteClass);
        }

        // Get corresponding method called in real object
        Class instanceClass = instance.getClass();
        Method calledMethod = instanceClass.getMethod(method.getName(), method.getParameterTypes());

        // Get method interceptors class and instantiate them
        List<Class> interceptorsClasses = BehaviourFinder.getInterceptorsFor(calledMethod);
        List<IInterceptor> interceptors = new ArrayList<>();

        // Instantiate interceptors
        for (Class interceptorClass : interceptorsClasses)
        {
            interceptors.add((IInterceptor) ContainerObjectFactory.createNewInstanceFor(interceptorClass));
        }

        // Run
        try
        {
            for (IInterceptor i : interceptors)
                i.before(instance, calledMethod, args);

            // Real method call
            ret = method.invoke(instance, args);

            for (IInterceptor i : interceptors)
                i.after(instance, calledMethod, args);
        }
        catch (Exception e)
        {
            for (IInterceptor i : interceptors)
                i.onError(instance, e, calledMethod, args);
        }
        
        return ret;
    }

    public Object getInstance()
    {
        return instance;
    }
}
