package fr.isima.dependencyinjector.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferedClassFound;
import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.EJBContainer;
import fr.isima.dependencyinjector.service.interfaces.ITransactionalService;
import fr.isima.dependencyinjector.transaction.ITransaction;
import fr.isima.dependencyinjector.transaction.TransactionMock;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alraberin1
 */
public class TransactionalTest 
{
    @Inject
    private ITransaction transaction;
    
    @Inject
    private ITransactionalService service;
    
    @Before
    public void setUp() throws TooMuchPreferedClassFound, NoConcreteClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }
    
    @Test
    public void transactional()
    {
        assertNotNull(service);
        assertNotNull(transaction);
        
        assertTrue(transaction instanceof TransactionMock);
        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
        
        service.method();
        
        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(1, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }
}
