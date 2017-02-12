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
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.interfaces.IService;
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
        
        assertEquals("success", service.foo());
        assertEquals("success", otherService.foo());
        
        assertSame(service, otherService);
    }
}
