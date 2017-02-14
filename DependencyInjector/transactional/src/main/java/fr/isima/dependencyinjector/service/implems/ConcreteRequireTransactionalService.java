/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.service.implems;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional;
import fr.isima.dependencyinjector.entitymanager.IEntityManager;
import fr.isima.dependencyinjector.service.interfaces.IRequireTransactionalService;

import static fr.isima.dependencyinjector.annotations.Transactional.TransactionType.REQUIRE;

/**
 *
 * @author kernelith
 */
public class ConcreteRequireTransactionalService implements IRequireTransactionalService
{
    @Inject
    private IEntityManager em;

    @Override
    @Transactional(type = REQUIRE)
    public void methodSucceed()
    {
    }

    @Override
    @Transactional(type = REQUIRE)
    public void methodImbricationSucceed()
    {
        em.execSimpleSuccessQuery();
    }

    @Override
    @Transactional(type = REQUIRE)
    public void methodMixedSucceed()
    {
        em.execSuccessQuery();
    }

    @Override
    @Transactional(type = REQUIRE)
    public void methodFailed()
    {
        throw new RuntimeException("Impossible to finish execution of transactional method");
    }

    @Override
    @Transactional(type = REQUIRE)
    public void methodImbricationFailed()
    {
        em.execSimpleFailedQuery();
    }

    @Override
    @Transactional(type = REQUIRE)
    public void methodMixedFailed()
    {
        em.execFailedQuery();
    }
}
