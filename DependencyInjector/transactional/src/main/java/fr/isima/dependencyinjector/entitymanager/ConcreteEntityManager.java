/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.entitymanager;

import fr.isima.dependencyinjector.annotations.Transactional;

import static fr.isima.dependencyinjector.annotations.Transactional.TransactionType.REQUIRE;
import static fr.isima.dependencyinjector.annotations.Transactional.TransactionType.REQUIRE_NEW;

/**
 *
 * @author kernelith
 */
public class ConcreteEntityManager implements IEntityManager 
{
    @Override
    @Transactional(type = REQUIRE)
    public void execSimpleSuccessQuery()
    {
        System.out.println("Exec good query");
    }

    @Override
    @Transactional(type = REQUIRE)
    public void execSimpleFailedQuery()
    {
        System.out.println("Exec bad query");
        throw new RuntimeException("Fail to exec query");
    }

    @Override
    @Transactional(type = REQUIRE_NEW)
    public void execSuccessQuery()
    {
        System.out.println("Exec Require New good query");
    }

    @Override
    @Transactional(type = REQUIRE_NEW)
    public void execFailedQuery()
    {
        System.out.println("Exec Require New bad query");
        throw new RuntimeException("Fail to exec query");
    }
}
