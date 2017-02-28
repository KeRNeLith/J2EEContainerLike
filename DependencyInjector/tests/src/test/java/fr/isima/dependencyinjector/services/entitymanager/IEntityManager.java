/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.services.entitymanager;

/**
 *
 * @author kernelith
 */
public interface IEntityManager 
{
    void execSimpleSuccessQuery();
    void execSuccessQuery();

    void execSimpleFailedQuery();
    void execFailedQuery();
}
