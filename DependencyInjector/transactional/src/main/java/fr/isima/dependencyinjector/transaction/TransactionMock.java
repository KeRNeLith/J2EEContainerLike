/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.transaction;

import fr.isima.dependencyinjector.injector.annotations.Prefered;

/**
 *
 * @author alraberin1
 */
@Prefered
public class TransactionMock implements ITransaction
{
    private static int nbBegins = 0;
    private static int nbCommits = 0;
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
    
    // Accesseurs
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