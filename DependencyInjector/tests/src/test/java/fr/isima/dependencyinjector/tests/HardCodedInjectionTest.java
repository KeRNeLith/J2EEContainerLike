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
public class HardCodedInjectionTest
{    
    @Inject
    private IService service;
    
    @Before
    public void setUp() throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
    {
        // Register correspondences
        EJBContainer.getInjector().registerType(IService.class, ServiceImplm.class);
        
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    @Test
    public void injectionDependency1() 
    {
        Class s = EJBContainer.getInjector().resolveType(IService.class);
        assertEquals(s, ServiceImplm.class);
    }
    
    @Test
    public void injectionDependency2() 
    {
        assertNotNull(service);
        assertTrue(Proxy.isProxyClass(service.getClass()));

        assertEquals("success", service.foo());

        // Check Implementation type behind proxy class
        InvocationHandler handler = Proxy.getInvocationHandler(service);
        assertTrue(handler instanceof ContainerInvocationHandler);
        ContainerInvocationHandler containerHandler = (ContainerInvocationHandler) handler;
        assertTrue(containerHandler.getInstance() instanceof ServiceImplm);
    }
}
