/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.annotations.Preferred;
import fr.isima.dependencyinjector.injector.interfaces.ISuperService;

/**
 *
 * @author kernelith
 */
@Preferred
public class PreferredSuperService implements ISuperService
{
    @Override
    public String superFoo() 
    {
        return "success";
    }    
}
