/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.transaction;

/**
 *
 * @author alraberin1
 */
public interface ITransaction
{
    void begin();

    void commit();

    void rollback();
}
