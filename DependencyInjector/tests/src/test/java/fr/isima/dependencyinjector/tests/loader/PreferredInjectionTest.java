package fr.isima.dependencyinjector.tests.loader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.ContainerInvocationHandler;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.implems.PreferredSuperService;
import fr.isima.dependencyinjector.injector.interfaces.ISuperService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 *
 * @author kernelith
 */
public class PreferredInjectionTest
{
    @Inject
    private ISuperService service;         // 2 Implems
    
    @Before
    public void setUp() throws TooMuchPreferredClassFound, NoConcreteClassFound, TooMuchConcreteClassFound
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    @Test
    public void injectionDependencyLoaderNoChoice() 
    {
        assertNotNull(service);
        assertTrue(Proxy.isProxyClass(service.getClass()));

        // Check Implementation type behind proxy class
        InvocationHandler handler = Proxy.getInvocationHandler(service);
        assertTrue(handler instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler = (ContainerInvocationHandler) handler;
        assertTrue(containerHandler.getObject() instanceof PreferredSuperService);

        assertEquals("success", service.superFoo());
    }
}
