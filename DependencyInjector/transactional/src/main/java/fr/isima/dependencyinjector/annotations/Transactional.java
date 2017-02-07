/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.annotations;

import fr.isima.dependencyinjector.injector.annotations.Behaviour;
import fr.isima.dependencyinjector.transaction.TransactionInterceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *
 * @author kernelith
 */
@Target(ElementType.METHOD)
@Behaviour(interceptor = TransactionInterceptor.class)
public @interface Transactional 
{
    enum TransactionType 
    {
        REQUIRE,
        REQUIRE_NEW
    }
    
    TransactionType type();
}
