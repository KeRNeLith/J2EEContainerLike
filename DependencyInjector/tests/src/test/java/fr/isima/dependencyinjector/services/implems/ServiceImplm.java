/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.services.implems;

import fr.isima.dependencyinjector.annotations.Singleton;
import fr.isima.dependencyinjector.services.interfaces.IService;

/**
 *
 * @author kernelith
 */
@Singleton
public class ServiceImplm implements IService
{
    @Override
    public String foo() 
    {
        return "success";
    }
}
