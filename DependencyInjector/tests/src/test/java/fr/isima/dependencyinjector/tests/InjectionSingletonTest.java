package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferedClassFound;
import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.interfaces.IService;
import fr.isima.dependencyinjector.injector.implems.ServiceImplm;
import org.junit.Before;
import org.junit.Test;
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
    public void setUp() throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }

    @Test
    public void injectionSingleton() 
    {
        assertNotNull(service);
        assertNotNull(otherService);
        
        assertTrue(service instanceof ServiceImplm);
        assertTrue(otherService instanceof ServiceImplm);
        
        assertEquals("success", service.foo());
        assertEquals("success", otherService.foo());
        
        assertSame(service, otherService);
    }
}
