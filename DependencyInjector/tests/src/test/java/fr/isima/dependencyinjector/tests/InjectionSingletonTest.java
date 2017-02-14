package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.bootstrap.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.handlers.ContainerInvocationHandler;
import fr.isima.dependencyinjector.services.implems.ServiceImplm;
import fr.isima.dependencyinjector.services.interfaces.IService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 *
 * @author kernelith
 */
public class InjectionSingletonTest
{    
    @Inject
    private IService service;
    
    @Inject
    private IService otherService;
    
    @Before
    public void setUp() throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }

    @Test
    public void injectionSingleton() 
    {
        assertNotNull(service);
        assertNotNull(otherService);
        assertTrue(Proxy.isProxyClass(service.getClass()));
        assertTrue(Proxy.isProxyClass(otherService.getClass()));

        assertEquals("success", service.foo());
        assertEquals("success", otherService.foo());

        // Check Implementation type behind proxies class
        InvocationHandler handler1 = Proxy.getInvocationHandler(service);
        assertTrue(handler1 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler1 = (ContainerInvocationHandler) handler1;
        assertTrue(containerHandler1.getInstance() instanceof ServiceImplm);
        ServiceImplm serviceImplm1 = (ServiceImplm) containerHandler1.getInstance();

        InvocationHandler handler2 = Proxy.getInvocationHandler(service);
        assertTrue(handler2 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler2 = (ContainerInvocationHandler) handler2;
        assertTrue(containerHandler2.getInstance() instanceof ServiceImplm);
        ServiceImplm serviceImplm2 = (ServiceImplm) containerHandler2.getInstance();

        assertSame(serviceImplm1, serviceImplm2);
    }
}
