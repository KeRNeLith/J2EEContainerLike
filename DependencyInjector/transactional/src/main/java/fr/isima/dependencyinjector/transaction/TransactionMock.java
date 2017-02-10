/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.annotations.Preferred;
import fr.isima.dependencyinjector.annotations.Singleton;

/**
 *
 * @author kernelith
 */
@Preferred
// Remove ?
//@Singleton
public class TransactionMock implements ITransaction
{
    private static int nbBegins = 0;
    private static int nbCommits = 0;
    private static int nbRollbacks = 0;
    /*private int nbBegins = 0;
    private int nbCommits = 0;
    private int nbRollbacks = 0;*/

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
    
    // Accessors
    /*public int getNbBegins()
    {
        return nbBegins;
    }

    public int getNbCommits()
    {
        return nbCommits;
    }

    public int getNbRollbacks()
    {
        return nbRollbacks;
    }*/
    public static void reset()
    {
        nbBegins = 0;
        nbCommits = 0;
        nbRollbacks = 0;
    }

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