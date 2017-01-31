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
import fr.isima.dependencyinjector.injector.implems.PreferedSuperService;
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
    
    //@Inject
    //private ISeftInjectService selfService;
    
    @Before
    public void setUp() throws TooMuchPreferedClassFound, NoConcreteClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests
    @Test
    public void injectionDependencyCascade() 
    {
        assertNotNull(service);
        assertTrue(service instanceof ConcreteCascadeService);
        ConcreteCascadeService cascadeService = (ConcreteCascadeService) service;
        
        // Recurse level 1
        assertNotNull(cascadeService.normalService);
        assertTrue(cascadeService.normalService instanceof NormalServiceImplm);
        NormalServiceImplm normalService = (NormalServiceImplm) cascadeService.normalService;
        
        // Recurse level 2
        assertNotNull(normalService.superService);
        assertTrue(normalService.superService instanceof PreferedSuperService);
        
        // Work for n + 1 and n + 2 => so work for every n
        
        assertEquals("success", service.cascadeFoo());
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
