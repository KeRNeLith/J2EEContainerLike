/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.annotations;

import fr.isima.dependencyinjector.annotations.Behaviour;
import fr.isima.dependencyinjector.logging.LogInterceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author kernelith
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Behaviour(interceptor = LogInterceptor.class)
public @interface Log 
{
    
}
