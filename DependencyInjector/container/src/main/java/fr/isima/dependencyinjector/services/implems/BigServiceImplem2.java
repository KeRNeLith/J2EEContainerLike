/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.services.implems;

import fr.isima.dependencyinjector.services.interfaces.IBigService;

/**
 *
 * @author kernelith
 */
public class BigServiceImplem2 implements IBigService
{
    public String bigFoo() 
    {
        return "success";
    }
}
