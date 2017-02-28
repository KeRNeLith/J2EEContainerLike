/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.services.implems;

import fr.isima.dependencyinjector.annotations.Log;
import fr.isima.dependencyinjector.services.interfaces.ILoggedService;

/**
 *
 * @author kernelith
 */
public class ConcreteLoggedService implements ILoggedService
{
    @Override
    @Log    // Always at implementation level !
    public void method1() 
    {
        // Do something
    }
    
    @Override
    public void method2() 
    {
        // Do something
    }
}
