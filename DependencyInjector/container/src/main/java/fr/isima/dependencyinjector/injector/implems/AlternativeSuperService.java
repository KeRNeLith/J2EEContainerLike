/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.injector.interfaces.ISuperService;

/**
 *
 * @author kernelith
 */
public class AlternativeSuperService implements ISuperService
{
    @Override
    public String superFoo() 
    {
        return "success";
    }
}
