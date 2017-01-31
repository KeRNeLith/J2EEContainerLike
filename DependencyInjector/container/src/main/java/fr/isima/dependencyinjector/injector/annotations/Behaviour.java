/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector.annotations;

import fr.isima.dependencyinjector.injector.interceptor.IInterceptor;

/**
 *
 * @author alraberin1
 */
public @interface Behaviour 
{
    Class<? extends IInterceptor> interceptor();
}
