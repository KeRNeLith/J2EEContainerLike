/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.entitymanager;

/**
 *
 * @author alraberin1
 */
public class ConcreteEntityManager implements IEntityManager 
{
    @Override
    public void execQuery(String query) 
    {
        System.out.println("Exec query: " + query);
    }
}
