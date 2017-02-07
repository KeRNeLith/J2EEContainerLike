/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.service.implems;

import fr.isima.dependencyinjector.annotations.Transactional;
import static fr.isima.dependencyinjector.annotations.Transactional.TransactionType.REQUIRE;
import fr.isima.dependencyinjector.service.interfaces.IRequireTransactionalService;

/**
 *
 * @author alraberin1
 */
public class ConcreteTransactionalService implements IRequireTransactionalService
{
    @Override
    @Transactional(type = REQUIRE)
    public void methodSucceed()
    {
    }

    @Override
    @Transactional(type = REQUIRE)
    public void methodFailed()
    {
        throw new RuntimeException("Impossible to finish execution of transactional method");
    }
}
