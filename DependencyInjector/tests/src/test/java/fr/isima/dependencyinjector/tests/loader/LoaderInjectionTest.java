package fr.isima.dependencyinjector.tests.loader;

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
import fr.isima.dependencyinjector.services.implems.NormalServiceImplm;
import fr.isima.dependencyinjector.services.interfaces.INormalService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 *
 * @author kernelith
 */
public class LoaderInjectionTest 
{
    @Inject
    private INormalService normalService;   // 1 Implem
    
    @Before
    public void setUp() throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests    
    @Test
    public void injectionDependencyLoader() 
    {
        assertNotNull(normalService);
        assertTrue(Proxy.isProxyClass(normalService.getClass()));

        assertEquals("success", normalService.normalFoo());

        // Check Implementation type behind proxy class
        InvocationHandler handler = Proxy.getInvocationHandler(normalService);
        assertTrue(handler instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler = (ContainerInvocationHandler) handler;
        assertTrue(containerHandler.getInstance() instanceof NormalServiceImplm);
    }
}
