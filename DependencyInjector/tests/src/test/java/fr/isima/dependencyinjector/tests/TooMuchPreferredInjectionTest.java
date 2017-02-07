package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.interfaces.IHugeService;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author kernelith
 */
public class TooMuchPreferredInjectionTest
{
    @Inject
    private IHugeService service;         // 2 Implems
    
    // Tests
    @Test(expected = TooMuchPreferredClassFound.class)
    public void injectionDependencyTooMuchPreferred() throws TooMuchPreferredClassFound, NoConcreteClassFound, TooMuchConcreteClassFound
    {
        assertNull(service);
        
        // Injection
        EJBContainer.getInjector().inject(this);
    }
}
