package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferedClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.interfaces.IBigService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kernelith
 */
public class LoaderTooMuchInjectionTest
{
    @Inject
    private IBigService bigService;         // 2 Implems
    
    // Tests
    @Test(expected = TooMuchConcreteClassFound.class)
    public void injectionDependencyLoaderTooMuchChoice() throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound 
    {
        assertNull(bigService);
        
        // Injection
        EJBContainer.getInjector().inject(this);
    }
}
