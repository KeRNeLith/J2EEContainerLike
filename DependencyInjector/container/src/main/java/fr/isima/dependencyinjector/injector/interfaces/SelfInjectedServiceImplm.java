/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.interfaces;

import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.annotations.Singleton;

/**
 *
 * @author kernelith
 */
@Singleton
public class SelfInjectedServiceImplm implements ISeftInjectService
{
    @Inject
    public ISeftInjectService service;
    
    @Override
    public String selfFoo() 
    {
        return "success";
    }
}
