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
import fr.isima.dependencyinjector.injector.interfaces.INormalService;
import fr.isima.dependencyinjector.injector.implems.NormalServiceImplm;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author alraberin1
 */
public class LoaderInjectionTest 
{
    @Inject
    private INormalService normalService;   // 1 Implem
    
    @Before
    public void setUp() throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    // Tests    
    @Test
    public void injectionDependencyLoader() 
    {
        assertNotNull(normalService);
        assertTrue(normalService instanceof NormalServiceImplm);
        assertEquals("success", normalService.normalFoo());
    }
}
