/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.injector.interfaces.ISuperService;
import fr.isima.dependencyinjector.injector.annotations.Prefered;

/**
 *
 * @author alraberin1
 */
@Prefered
public class PreferedSuperService implements ISuperService
{
    @Override
    public String superFoo() 
    {
        return "success";
    }    
}
