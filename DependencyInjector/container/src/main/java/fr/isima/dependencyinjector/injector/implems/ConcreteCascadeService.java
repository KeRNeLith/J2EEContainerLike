/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.implems;

import fr.isima.dependencyinjector.injector.interfaces.INormalService;
import fr.isima.dependencyinjector.injector.interfaces.ICascadeService;
import fr.isima.dependencyinjector.annotations.Inject;

/**
 *
 * @author kernelith
 */
public class ConcreteCascadeService implements ICascadeService
{
    @Inject
    public INormalService normalService;
    
    @Override
    public String cascadeFoo() 
    {
        return "success " + normalService.cascadeFoo();
    }
}
