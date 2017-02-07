/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.interceptor.IInterceptor;

import java.lang.reflect.Method;

/**
 *
 * @author kernelith
 */
public class TransactionInterceptor implements IInterceptor
{
    @Inject
    private ITransaction transcation;
    
    @Override
    public void before(Object instance, Method method, Object... parameters) 
    {
        transcation.begin();
    }

    @Override
    public void after(Object instance, Method method, Object... parameters) 
    {
        transcation.commit();
    }
    
    @Override
    public void onError(Object instance, Method method, Object... parameters) 
    {
        transcation.rollback();
    }
}
