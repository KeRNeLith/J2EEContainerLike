package fr.isima.dependencyinjector.service.implems;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Transactional;
import fr.isima.dependencyinjector.entitymanager.IEntityManager;
import fr.isima.dependencyinjector.service.interfaces.IRequireNewTransactionalService;

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
	public void methodSimpleSucceed()
	{
		System.out.println("Simple success");
	}

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
	public void methodSimpleFailed() throws Exception
	{
		throw new RuntimeException("Fail to exec");
	}

	@Override
	@Transactional(type = REQUIRE_NEW)
	public void methodFailed() throws Exception
	{
		em.execFailedQuery();
	}

	@Override
	@Transactional(type = REQUIRE_NEW)
	public void methodMixedFailed() throws Exception
	{
		em.execSimpleFailedQuery();
	}
}
