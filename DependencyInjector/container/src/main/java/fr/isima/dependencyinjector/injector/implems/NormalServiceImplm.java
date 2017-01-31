/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.injector.interfaces.INormalService;

/**
 *
 * @author alraberin1
 */
public class NormalServiceImplm implements INormalService
{
    @Override
    public String normalFoo() 
    {
        return "success";
    }
}
