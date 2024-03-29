/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.annotations.Preferred;

/**
 *
 * @author kernelith
 */
@Preferred
public class TransactionMock implements ITransaction
{
    /**
     * Number of begin done.
     */
    private static int nbBegins = 0;

    /**
     * Number of commit done.
     */
    private static int nbCommits = 0;

    /**
     * Number of rollback done.
     */
    private static int nbRollbacks = 0;

    @Override
    public void begin() 
    {
        ++nbBegins;
    }

    @Override
    public void commit() 
    {
        ++nbCommits;
    }

    @Override
    public void rollback() 
    {
        ++nbRollbacks;
    }

    /**
     * Reset statistic counters.
     */
    public static void reset()
    {
        nbBegins = 0;
        nbCommits = 0;
        nbRollbacks = 0;
    }

    // Accessors
    public static int getNbBegins()
    {
        return nbBegins;
    }
    
    public static int getNbCommits()
    {
        return nbCommits;
    }
    
    public static int getNbRollbacks()
    {
        return nbRollbacks;
    }
}