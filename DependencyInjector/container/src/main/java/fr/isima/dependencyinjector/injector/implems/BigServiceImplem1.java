/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.injector.interfaces.IBigService;

/**
 *
 * @author alraberin1
 */
public class BigServiceImplem1 implements IBigService
{
    @Override
    public String bigFoo() 
    {
        return "success";
    }
}
