/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.services.implems;

import fr.isima.dependencyinjector.services.interfaces.IHugeService;

/**
 *
 * @author kernelith
 */
public class AlternativeHugeService implements IHugeService
{
    @Override
    public String hugeFoo() 
    {
        return "success";
    }
}
