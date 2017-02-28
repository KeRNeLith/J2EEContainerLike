package fr.isima.dependencyinjector.services.implems;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional;
import fr.isima.dependencyinjector.services.entitymanager.IEntityManager;
import fr.isima.dependencyinjector.services.interfaces.IRequireNewTransactionalService;

import static fr.isima.dependencyinjector.annotations.Transactional.TransactionType.REQUIRE_NEW;

/**
 * Created by kernelith on 07/02/17.
 */
public class ConcreteRequireNewTransactionalService implements IRequireNewTransactionalService
{
	@Inject
	private IEntityManager em;

	@Override
	@Transactional(type = REQUIRE_NEW)
	public void methodSucceed()
	{
		em.execSuccessQuery();
	}

	@Override
	@Transactional(type = REQUIRE_NEW)
	public void methodMixedSucceed()
	{
		em.execSimpleSuccessQuery();
	}

	@Override
	@Transactional(type = REQUIRE_NEW)
	public void methodFailed()
	{
		em.execFailedQuery();
	}

	@Override
	@Transactional(type = REQUIRE_NEW)
	public void methodMixedFailed()
	{
		em.execSimpleFailedQuery();
	}
}
