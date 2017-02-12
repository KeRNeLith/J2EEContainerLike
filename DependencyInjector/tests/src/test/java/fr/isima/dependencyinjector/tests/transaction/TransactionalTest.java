package fr.isima.dependencyinjector.tests.transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
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
    public void setUp() throws TooMuchPreferredClassFound, NoConcreteClassFound, TooMuchConcreteClassFound
    {
        // Injection
        EJBContainer.getInjector().inject(this);
    }

    // Tests
    // Require tests
    @Test
    public void requireSucceedTransaction()
    {
        TransactionMock.reset();

        assertNotNull(requireService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodSucceed();
        
        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(1, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }

    @Test
    public void requireImbricationSucceedTransaction()
    {
        TransactionMock.reset();

        assertNotNull(requireService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodImbricationSucceed();

        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(1, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }

    @Test
    public void requireMixedBeforeRequireSucceedTransaction()
    {
        TransactionMock.reset();

        assertNotNull(requireService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodMixedSucceed();

        assertEquals(2, TransactionMock.getNbBegins());
        assertEquals(2, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }

    @Test(expected = Exception.class)
    public void requireFailedTransaction() throws Exception
    {
        TransactionMock.reset();

        assertNotNull(requireService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodFailed();

        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(1, TransactionMock.getNbRollbacks());
    }

    @Test(expected = Exception.class)
    public void requireImbricationFailedTransaction() throws Exception
    {
        TransactionMock.reset();

        assertNotNull(requireService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodImbricationFailed();

        assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(1, TransactionMock.getNbRollbacks());
    }

    @Test(expected = Exception.class)
    public void requireMixedBeforeRequireFailedTransaction() throws Exception
    {
        TransactionMock.reset();

        assertNotNull(requireService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireService.methodMixedFailed();

        assertEquals(2, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(2, TransactionMock.getNbRollbacks());
    }

    // Require New Tests
    @Test
    public void requireNewSucceedTransaction()
    {
        TransactionMock.reset();

        assertNotNull(requireNewService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireNewService.methodSucceed();

        assertEquals(2, TransactionMock.getNbBegins());
        assertEquals(2, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());
    }

    @Test
    public void requireNewMixedBeforeRequireNewSucceedTransaction()
    {
        TransactionMock.reset();

        assertNotNull(requireNewService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireNewService.methodMixedSucceed();

        /*assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(1, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());*/
        // TODO
        System.out.println(TransactionMock.getNbBegins() + " " + TransactionMock.getNbCommits() + " " + TransactionMock.getNbRollbacks());
        System.out.println("Fin");

        fail("TODO");
    }

    @Test(expected = Exception.class)
    public void requireNewFailedTransaction() throws Exception
    {
        TransactionMock.reset();

        assertNotNull(requireNewService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireNewService.methodFailed();

        assertEquals(2, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(2, TransactionMock.getNbRollbacks());
    }

    @Test(expected = Exception.class)
    public void requireNewMixedBeforeRequireNewFailedTransaction() throws Exception
    {
        TransactionMock.reset();

        assertNotNull(requireNewService);
        assertNotNull(transaction);

        assertEquals(0, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(0, TransactionMock.getNbRollbacks());

        requireNewService.methodMixedFailed();

        // TODO
        /*assertEquals(1, TransactionMock.getNbBegins());
        assertEquals(0, TransactionMock.getNbCommits());
        assertEquals(1, TransactionMock.getNbRollbacks());*/
        System.out.println("Fin");
        fail("TODO");
    }
}
