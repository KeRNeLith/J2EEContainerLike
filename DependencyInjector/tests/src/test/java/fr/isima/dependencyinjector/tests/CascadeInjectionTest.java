package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.handlers.ContainerInvocationHandler;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.services.implems.ConcreteCascadeService;
import fr.isima.dependencyinjector.services.implems.NormalServiceImplm;
import fr.isima.dependencyinjector.services.implems.PreferredSuperService;
import fr.isima.dependencyinjector.services.interfaces.ICascadeService;
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
    
    //@Inject
    //private ISeftInjectService selfService;
    
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

        // Check Implementation type behind proxy class
        InvocationHandler handler = Proxy.getInvocationHandler(service);
        assertTrue(handler instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler = (ContainerInvocationHandler) handler;
        assertTrue(containerHandler.getObject() instanceof ConcreteCascadeService);

        ConcreteCascadeService cascadeService = (ConcreteCascadeService) containerHandler.getObject();
        
        // Recurse level 1
        assertNotNull(cascadeService.normalService);
        assertTrue(Proxy.isProxyClass(cascadeService.normalService.getClass()));

        // Check Implementation type behind proxy class
        InvocationHandler handler2 = Proxy.getInvocationHandler(cascadeService.normalService);
        assertTrue(handler2 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler2 = (ContainerInvocationHandler) handler2;
        assertTrue(containerHandler2.getObject() instanceof NormalServiceImplm);

        NormalServiceImplm normalService = (NormalServiceImplm) containerHandler2.getObject();
        
        // Recurse level 2
        assertNotNull(normalService.superService);
        assertTrue(Proxy.isProxyClass(normalService.superService.getClass()));

        // Check Implementation type behind proxy class
        InvocationHandler handler3 = Proxy.getInvocationHandler(normalService.superService);
        assertTrue(handler3 instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler3 = (ContainerInvocationHandler) handler3;
        assertTrue(containerHandler3.getObject() instanceof PreferredSuperService);
        
        // Work for n + 1 and n + 2 => so work for every n
        
        assertEquals("success success success", service.cascadeFoo());
    }
    
    // TODO test self inject 
    /*@Test
    public void injectionDependencySelfInject() 
    {
        assertNotNull(selfService);
        assertTrue(selfService instanceof SelfInjectedServiceImplm);
        SelfInjectedServiceImplm sService = (SelfInjectedServiceImplm) selfService;
        
        assertNotNull(sService.service);
        assertTrue(sService.service instanceof SelfInjectedServiceImplm);

        assertEquals("success", selfService.selfFoo());
    }*/
}
