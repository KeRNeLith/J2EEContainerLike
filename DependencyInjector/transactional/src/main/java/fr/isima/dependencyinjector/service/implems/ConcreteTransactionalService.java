/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.service.implems;

import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional;
import static fr.isima.dependencyinjector.annotations.Transactional.TransactionType.REQUIRE;
import fr.isima.dependencyinjector.entitymanager.IEntityManager;
import fr.isima.dependencyinjector.service.interfaces.ITransactionalService;

/**
 *
 * @author alraberin1
 */
public class ConcreteTransactionalService implements ITransactionalService
{
    @Inject
    private IEntityManager em;
    
    @Override
    @Transactional(type = REQUIRE)
    public void method()
    {
        em.execQuery("SELECT * FROM NoWhere");
    }
    
    /*class Service
    {
        @Inject
        Service2 s2;
        
        @Transactional(REQUIRE)
        void m()
        {
            s2.m2();
        }
    }
    
    class Service2
    {
        @Transactional(REQUIRE_NEW)
        void m2()
        {
            
        }
    }
    
    class Tester
    {
        @Inject
        Service service;

        @Test
        void test()
        {
            service.m();
        }
    }   */
}
