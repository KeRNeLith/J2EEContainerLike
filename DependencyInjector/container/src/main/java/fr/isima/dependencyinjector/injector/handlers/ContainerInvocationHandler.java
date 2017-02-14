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
    /**
     * Instance of the object handled by the proxy containing this invocation handler.
     */
    private Object instance;

    public ContainerInvocationHandler()
    {
        instance = null;
    }

    /**
     * Handle logic when calling a method of a dynamic proxy.
     * @param proxy Proxy instance called.
     * @param method Invoked method.
     * @param args Method arguments.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
    {
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

        // Construct responsibility chain
        InvocationContextChain chain = new InvocationContextChain(instance, calledMethod, args);
        chain.buildResponsibilityChain(interceptors);

        // Run chain
        return chain.execNextInterceptor();
    }

    public Object getInstance()
    {
        return instance;
    }
}
