/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.services.implems;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Singleton;
import fr.isima.dependencyinjector.services.interfaces.ISeftInjectService;

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
