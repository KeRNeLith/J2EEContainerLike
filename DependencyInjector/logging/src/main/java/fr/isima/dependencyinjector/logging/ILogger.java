/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.logging;

/**
 *
 * @author alraberin1
 */
public interface ILogger 
{
    boolean contains(String str);
    
    void add(String str);
}
