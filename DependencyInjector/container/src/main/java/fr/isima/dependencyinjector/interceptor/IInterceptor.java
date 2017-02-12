/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.interceptor;

import java.lang.reflect.Method;

/**
 *
 * @author kernelith
 */
public interface IInterceptor 
{
    void before(Object instance, Method method, Object... parameters);
    
    void after(Object instance, Method method, Object... parameters);
    
    void onError(Object instance, Exception exception, Method method, Object... parameters) throws Exception;
}