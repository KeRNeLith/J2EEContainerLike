package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferedClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.injector.interfaces.INotImplementedService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alraberin1
 */
public class LoaderNoChoiceInjectionTest
{
    @Inject
    private INotImplementedService service;               // 0 Implem
    
    // Tests
    @Test(expected = NoConcreteClassFound.class)
    public void injectionDependencyLoaderNoChoice() throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound 
    {
        assertNull(service);
        
        // Injection
        EJBContainer.getInjector().inject(this);
    }
}
