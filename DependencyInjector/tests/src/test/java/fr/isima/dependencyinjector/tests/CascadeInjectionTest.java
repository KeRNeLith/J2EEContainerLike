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
import fr.isima.dependencyinjector.services.implems.ConcreteCascadeService;
import fr.isima.dependencyinjector.services.implems.NormalServiceImplm;
import fr.isima.dependencyinjector.services.implems.PreferredSuperService;
import fr.isima.dependencyinjector.services.implems.SelfInjectedServiceImplm;
import fr.isima.dependencyinjector.services.interfaces.ICascadeService;
import fr.isima.dependencyinjector.services.interfaces.ISelfInjectService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 *
 * @author kernelith
 */
public class CascadeInjectionTest
{    
    @Inject
    private ICascadeService service;
    
    @Inject
    private ISelfInjectService selfService;
    
    @Before
    public void setUp() throws TooMuchPreferredClassFound, NoConcreteClassFound, TooMuchConcreteClassFound
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    @Test
    public void injectionDependencyCascade() 
    {
        assertNotNull(service);
        assertTrue(Proxy.isProxyClass(service.getClass()));

        // Work for n + 1 and n + 2 => so work for every n
        assertEquals("success success success", service.cascadeFoo());

        // Check Implementation type behind proxy class
        InvocationHandler handler = Proxy.getInvocationHandler(service);
        assertTrue(handler instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler = (ContainerInvocationHandler) handler;
        assertTrue(containerHandler.getInstance() instanceof ConcreteCascadeService);

        ConcreteCascadeService cascadeService = (ConcreteCascadeService) containerHandler.getInstance();
        
        // Recurse level 1
        assertNotNull(cascadeService.normalService);
        assertTrue(Proxy.isProxyClass(cascadeService.normalService.getClass()));

        // Check Implementation type behind proxy class
        InvocationHandler handler2 = Proxy.getInvocationHandler(cascadeService.normalService);
        assertTrue(handler2 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler2 = (ContainerInvocationHandler) handler2;
        assertTrue(containerHandler2.getInstance() instanceof NormalServiceImplm);

        NormalServiceImplm normalService = (NormalServiceImplm) containerHandler2.getInstance();
        
        // Recurse level 2
        assertNotNull(normalService.superService);
        assertTrue(Proxy.isProxyClass(normalService.superService.getClass()));

        // Check Implementation type behind proxy class
        InvocationHandler handler3 = Proxy.getInvocationHandler(normalService.superService);
        assertTrue(handler3 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler3 = (ContainerInvocationHandler) handler3;
        assertTrue(containerHandler3.getInstance() instanceof PreferredSuperService);
    }

    @Test
    public void injectionDependencySelfInject()
    {
        assertNotNull(selfService);
        assertTrue(Proxy.isProxyClass(selfService.getClass()));

        assertEquals("success", selfService.selfFoo());

        // Check Implementation type behind proxy class
        InvocationHandler handler1 = Proxy.getInvocationHandler(selfService);
        assertTrue(handler1 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler1 = (ContainerInvocationHandler) handler1;
        assertTrue(containerHandler1.getInstance() instanceof SelfInjectedServiceImplm);
        SelfInjectedServiceImplm sService = (SelfInjectedServiceImplm) containerHandler1.getInstance();
    }
}
