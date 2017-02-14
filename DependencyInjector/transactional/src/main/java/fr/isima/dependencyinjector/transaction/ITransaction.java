/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.transaction;

/**
 *
 * @author kernelith
 */
public interface ITransaction
{
    /**
     * Begin a new transaction.
     */
    void begin();

    /**
     * Commit current running transaction.
     */
    void commit();

    /**
     * Rollback running transaction.
     */
    void rollback();
}
