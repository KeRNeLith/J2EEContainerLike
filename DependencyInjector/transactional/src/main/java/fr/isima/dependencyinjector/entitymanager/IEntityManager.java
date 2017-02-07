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
public interface IEntityManager 
{
    void execSuccessQuery();

    void execFailedQuery();
}
