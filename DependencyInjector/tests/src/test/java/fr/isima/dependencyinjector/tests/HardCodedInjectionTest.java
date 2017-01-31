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
 * @author alraberin1
 */
public class HardCodedInjectionTest
{    
    @Inject
    private IService service;
    
    @Before
    public void setUp() throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound 
    {
        // Enregistre les correspondances
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
        assertTrue(service instanceof ServiceImplm);
        assertEquals("success", service.foo());
    }
}
