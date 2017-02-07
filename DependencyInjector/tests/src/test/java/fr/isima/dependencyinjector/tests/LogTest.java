package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.logging.ILogger;
import fr.isima.dependencyinjector.service.interfaces.ILoggedService;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kernelith
 */
public class LogTest 
{
    @Inject
    private ILogger logger;
    
    @Inject
    private ILoggedService service;
    
    @Before
    public void setUp() throws TooMuchPreferredClassFound, NoConcreteClassFound, TooMuchConcreteClassFound
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    @Test
    public void loggedMethod() 
    {
        assertNotNull(service);
        assertNotNull(logger);
        
        service.method1();
        assertTrue(logger.contains("method1"));
    }
    
    @Test
    public void notLoggedMethod() 
    {
        assertNotNull(service);
        assertNotNull(logger);
        
        service.method2();
        assertFalse(logger.contains("method2"));
    }
}
