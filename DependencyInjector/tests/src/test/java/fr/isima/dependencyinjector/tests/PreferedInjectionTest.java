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
import fr.isima.dependencyinjector.injector.interfaces.ISuperService;
import fr.isima.dependencyinjector.injector.implems.PreferedSuperService;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kernelith
 */
public class PreferedInjectionTest 
{
    @Inject
    private ISuperService service;         // 2 Implems
    
    @Before
    public void setUp() throws TooMuchPreferedClassFound, NoConcreteClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    @Test
    public void injectionDependencyLoaderNoChoice() 
    {
        assertNotNull(service);
        assertTrue(service instanceof PreferedSuperService);
        assertEquals("success", service.superFoo());
    }
}
