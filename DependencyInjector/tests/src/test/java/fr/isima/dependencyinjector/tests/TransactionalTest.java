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
import fr.isima.dependencyinjector.service.interfaces.IRequireNewTransactionalService;
import fr.isima.dependencyinjector.service.interfaces.IRequireTransactionalService;
import fr.isima.dependencyinjector.transaction.ITransaction;
import fr.isima.dependencyinjector.transaction.TransactionMock;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kernelith
 */
public class TransactionalTest 
{
    @Inject
    private ITransaction transaction;
    
    @Inject
    private IRequireTransactionalService requireService;

    @Inject
    private IRequireNewTransactionalService requireNewService;
    
    @Before
    public void setUp() throws TooMuchPreferedClassFound, NoConcreteClassFound, TooMuchConcreteClassFound 
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }

    // Tests
    @Test
    public void requireSucceedTransaction()
    {
        assertNotNull(requireService);
        assertNotNull(transaction);
        
        assertTrue(transaction instanceof TransactionMock);
        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodSucceed();
        
        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(1, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }

    @Test
    public void requireFailedTransaction()
    {
        assertNotNull(requireService);
        assertNotNull(transaction);

        assertTrue(transaction instanceof TransactionMock);
        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodFailed();

        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(1, TransactionMock.getNbRollbacks());
    }

    @Test
    public void requireNewSucceedTransaction()
    {
        assertNotNull(requireNewService);
        assertNotNull(transaction);

        assertTrue(transaction instanceof TransactionMock);
        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireNewService.methodSucceed();

        assertEquals(2, TransactionMock.getNbBegins());
        assertEquals(2, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }

    @Test
    public void requireNewFailedTransaction()
    {
        assertNotNull(requireNewService);
        assertNotNull(transaction);

        assertTrue(transaction instanceof TransactionMock);
        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireNewService.methodFailed();

        assertEquals(2, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(2, TransactionMock.getNbRollbacks());
    }
}
