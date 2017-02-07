/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.interfaces.INormalService;
import fr.isima.dependencyinjector.injector.interfaces.ISuperService;

/**
 *
 * @author kernelith
 */
public class NormalServiceImplm implements INormalService
{
    @Inject
    public ISuperService superService;
    
    @Override
    public String normalFoo() 
    {
        return "success";
    }

    @Override
    public String cascadeFoo()
    {
        return "success " + superService.superFoo();
    }
}
