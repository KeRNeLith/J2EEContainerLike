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
import fr.isima.dependencyinjector.injector.implems.ConcreteCascadeService;
import fr.isima.dependencyinjector.injector.interfaces.ICascadeService;
import fr.isima.dependencyinjector.injector.implems.NormalServiceImplm;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author alraberin1
 */
public class CascadeInjectionTest
{    
    @Inject
    private ICascadeService service;
    
    @Before
    public void setUp() throws TooMuchPreferedClassFound, NoConcreteClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    // TODO test injection de soit meme
    
    @Test
    public void injectionDependencyCascade() 
    {
        assertNotNull(service);
        assertTrue(service instanceof ConcreteCascadeService);
        ConcreteCascadeService cascadeService = (ConcreteCascadeService) service;
        
        assertNotNull(cascadeService.normalService);
        assertTrue(cascadeService.normalService instanceof NormalServiceImplm);
        
        assertEquals("success", service.cascadeFoo());
    }
}
